import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import com.google.android.gms.tflite.client.TfLiteInitializationOptions
import com.google.android.gms.tflite.gpu.support.TfLiteGpu
import com.google.android.gms.tflite.java.TfLite
import org.tensorflow.lite.InterpreterApi
import org.tensorflow.lite.gpu.GpuDelegateFactory
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class TFLiteModelHelper(
    val context: Context,
    private val model: String = "recommender_model.tflite",
    private val onError: (String) -> Unit,
) {
    private var isGPUSupported: Boolean = false
    private var interpreter: InterpreterApi? = null

    init {
        TfLiteGpu.isGpuDelegateAvailable(context).onSuccessTask { gpuAvailable ->
            val optionsBuilder = TfLiteInitializationOptions.builder()
            if (gpuAvailable) {
                optionsBuilder.setEnableGpuDelegateSupport(true)
                isGPUSupported = true
            }
            TfLite.initialize(context, optionsBuilder.build())
        }.addOnSuccessListener {
            loadLocalModel()
        }.addOnFailureListener {
            onError("TFLite is not initialized yet.")
        }
    }

    private fun loadLocalModel() {
        try {
            val buffer: ByteBuffer = loadModelFile(context.assets, model)
            initializeInterpreter(buffer)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

    private fun initializeInterpreter(model: ByteBuffer) {
        interpreter?.close()
        try {
            val options = InterpreterApi.Options().setRuntime(InterpreterApi.Options.TfLiteRuntime.FROM_SYSTEM_ONLY)
            if (isGPUSupported) {
                options.addDelegateFactory(GpuDelegateFactory())
            }
            interpreter = InterpreterApi.create(model, options)
        } catch (e: Exception) {
            onError(e.message.toString())
            Log.e(TAG, e.message.toString())
        }
    }

    private fun loadModelFile(assetManager: AssetManager, modelPath: String): MappedByteBuffer {
        assetManager.openFd(modelPath).use { fileDescriptor ->
            FileInputStream(fileDescriptor.fileDescriptor).use { inputStream ->
                val fileChannel = inputStream.channel
                val startOffset = fileDescriptor.startOffset
                val declaredLength = fileDescriptor.declaredLength
                return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
            }
        }
    }

    fun predict(input: FloatArray): FloatArray {
        val inputBuffer = ByteBuffer.allocateDirect(4 * input.size).order(ByteOrder.nativeOrder())
        for (value in input) {
            inputBuffer.putFloat(value)
        }

        val outputBuffer = ByteBuffer.allocateDirect(4 * 1).order(ByteOrder.nativeOrder())
        interpreter?.run(inputBuffer, outputBuffer)
        outputBuffer.rewind()

        val output = FloatArray(1)
        outputBuffer.asFloatBuffer().get(output)
        return output
    }

    fun close() {
        interpreter?.close()
    }

    companion object {
        private const val TAG = "recommender_model.tflite"
    }
}
