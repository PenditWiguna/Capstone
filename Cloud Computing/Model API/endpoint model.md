# Endpoint Model
https://flask-bali-destination-ilmyfxcvzq-et.a.run.app

## Get Recommendations
- URL
    - /category-recommendation
- Method
    - GET
- Headers
    - Authorization: Bearer <token>
- Parameters
    - category as string
- Response
```json
{
    "recommendations": [
        {
            "Place_Name": "Istana Air Taman Ujung",
            "Alamat": "Taman Ujung, Br. Dinas...",
            "Description": "Tahun 1963, Taman Ujung di Kabupaten Karangasem, Bali pernah hancur lantaran...",
            "Gambar": "https://storage.googleapis.com/bucket/image/xxx.jpg"
        },
        {
            "Place_Name": "Pura Candi Tebing Gunung Kawi Sebatu",
            "Alamat": "Br penaka, Tampaksiring...",
            "Description": "Candi Gunung Kawi merupakan situs arkeologi berupa candi yang dipahatkan pada...",
            "Gambar": "https://storage.googleapis.com/bucket/image/xxx.jpg"
        },
        {
            "Place_Name": "Pura Tirta Empul",
            "Alamat": "Jl. Tirta, Manukaya...",
            "Description": "irta Empul adalah nama sebuah pura Hindu yang terletak di desa Manukaya...",
            "Gambar": "https://storage.googleapis.com/bucket/image/xxx.jpg"
        },
        {
            "Place_Name": "Tirta Gangga",
            "Alamat": "Jalan Raya Abang Desa Adat...",
            "Description": "Ada tiga hal utama yang ada di taman Tirta Gangga Karangasem, kebun, kolam air...",
            "Gambar": "https://storage.googleapis.com/bucket/image/xxx.jpg"
        },
        {
            "Place_Name": "Pura Luhur Batukaru",
            "Alamat": "Jl. Penatahan - Wongayagede...",
            "Description": "Pura Luhur Batukaru adalah sebuah pura yang terletak di lereng Gunung Batukaru...",
            "Gambar": "https://storage.googleapis.com/bucket/image/xxx.jpg"
        }
    ]
}
```

## Get Similar Destinations
- URL
    - /place-recommendation
- Method
    - GET
- Headers
    - Authorization: Bearer <token>
- Parameters
    - place_id as int
- Response
```json
{
    "recommendations": [
        {
            "Place_Name": "Goa Gajah",
            "Alamat": "Jalan Raya Goa Gajah No.99 Kemenuh Sukawati, Pejeng Kawan, Kec. Tampaksiring...",
            "Description": "Objek wisata Goa Gajah, sejatinya adalah peninggalan sejarah, seperti...",
            "Gambar": "https://storage.googleapis.com/bucket/image/xxx.jpg"
        },
        {
            "Place_Name": "Pura Taman Ayun",
            "Alamat": "Jl. Ayodya No.10, Mengwi, Kec. Mengwi, Kabupaten Badung, Bali",
            "Description": "Pura Taman Ayun di susun dengan konsep taman tradisional Bali yang...",
            "Gambar": "https://storage.googleapis.com/bucket/image/xxx.jpg"
        },
        {
            "Place_Name": "Istana Air Taman Ujung",
            "Alamat": "Taman Ujung, Br. Dinas, Tumbu, Kec. Karangasem, Kabupaten Karangasem, Bali",
            "Description": "ahun 1963, Taman Ujung di Kabupaten Karangasem, Bali pernah hancur...",
            "Gambar": "https://storage.googleapis.com/bucket/image/xxx.jpg"
        },
        {
            "Place_Name": "Pura Besakih",
            "Alamat": "Jl. Gunung Mas No.Ds, Besakih, Kec. Rendang, Kabupaten Karangasem, Bali",
            "Description": "Letak Pura Besakih sengaja dipilih di desa yang dianggap Kompleks...",
            "Gambar": "https://storage.googleapis.com/bucket/image/xxx.jpg"
        },
        {
            "Place_Name": "Tirta Gangga",
            "Alamat": "Jalan Raya Abang Desa Adat, Ababi, Kec. Abang, Kabupaten Karangasem...",
            "Description": "Ada tiga hal utama yang ada di taman Tirta Gangga Karangasem...",
            "Gambar": "https://storage.googleapis.com/bucket/image/xxx.jpg"
        }
    ]
}
```

## Get Destination Detail
- URL
    - /place-detail
- Method
    - GET
- Headers
    - Authorization: Bearer <token>
- Parameters
    - place_id as int
- Response
```json
{
    "place_detail": {
        "Alamat": "Jl. Serma Cok Ngurah Gambir Singapadu, Batubulan, Gianyar 80582 Indonesia",
        "Category": "Cagar Alam",
        "City": "Gianyar",
        "Description": "Atraksi wisata sekaligus tempat penangkaran berbagai jenis spesies burung di Indonesia maupun mancanegara. Selain menampilkan kehidupan alami berbagai jenis burung, bermacam-macam fasilitas dan program spesial diselenggarakan dengan fungsi mendidik dan penangkaran",
        "Gambar": "https://storage.googleapis.com/bucket/image/xxx.jpg",
        "Place_Name": "Bali Bird Park",
        "Rating": 4.5,
        "Weekday_Price": 95000,
        "Weekend_Holiday_Price": 110000
    }
}
```
## How to run
- Clone the code or download it as a zip file
- Configure your service account key
- Run "pip install -r requirements.txt" to install dependencies
- Run "python app.py" to run the app