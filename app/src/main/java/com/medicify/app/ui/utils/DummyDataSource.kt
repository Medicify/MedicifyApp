package com.medicify.app.ui.utils

import com.medicify.app.data.model.DrugItem

object DummyDataSource {
    fun getDrugItem(): DrugItem = DrugItem(
        id = "9a797f90-fd39-11ed-829f-4201ac1f7002",
        image = "https://d2qjkwm11akmwu.cloudfront.net/products/665462_20-3-2023_14-10-30.png",
        title = "Paracetamol 500 mg 10 Kaplet",
        type = "Strip",
        description = "PARACETAMOL TABLET merupakan obat yang dapat digunakan untuk meringankan rasa sakit pada sakit kepala, sakit gigi, dan menurunkan demam. Paracetamol bekerja pada pusat pengatur suhu di hipotalamus untuk menurunkan suhu tubuh (antipiretik) serta menghambat sintesis prostaglandin sehingga dapat mengurangi nyeri ringan sampai sedang (analgesik).",
        indication = "Obat ini digunakan untuk meredakan nyeri ringan hingga sedang seperti sakit kepala, sakit gigi, nyeri otot, serta menurunkan demam.",
        bpom = "BPOM: GBL7802318304A2* *) Obat ini merupakan obat Generik. Nomor Registrasi dapat berbeda sesuai dengan ketersediaan stock Apotek.",
        compotition = "Setiap tablet mengandung Paracetamol 500 mg",
        dose = "Dewasa: 1-2 kaplet, 3-4 kali per hari. Penggunaan maksimum 8 kaplet per hari. Anak 7-12 tahun: 0.5 - 1 kaplet, 3-4 kali per hari. Penggunaan maksimum 4 kaplet per hari.",
        howToUse = "Obat dapat diminum sebelum atau sesudah makan",
        attention = "Hati-hati penggunaan pada pasien dengan gagal ginjal, gangguan fungsi hati, dan alergi atau mengalami hipersensitivitas terhadap paracetamol. Kategori kehamilan: Kategori B: Mungkin dapat digunakan oleh wanita hamil. Penelitian pada hewan uji tidak memperlihatkan adanya risiko terhadap janin, namun belum ada bukti penelitian langsung terhadap wanita hamil.",
        indicationContra = "Parasetamol jangan diberikan kepada penderita hipersensitif/alergi terhadap Paracetamol. Penderita gangguan fungsi hati berat.",
        sideEffect = "Pemakaian obat umumnya memiliki efek samping tertentu dan sesuai dengan masing-masing individu. Jika terjadi efek samping yang berlebih dan berbahaya, harap konsultasikan kepada tenaga medis. Efek samping yang mungkin terjadi dalam penggunaan obat adalah: - Penggunaan untuk jangka waktu lama dan dosis besar dapat menyebabkan kerusakan fungsi hati. - Reaksi hipersensitivitas/alergi.",
        productClass = "Obat Bebas (Hijau)",
        jsonMemberPackage = "Dus, 10 Strip @ 10 Tablet",
        manufactur = "Generic Manufacturer",
        productUrl = "https://www.halodoc.com/obat-dan-vitamin/paracetamol-500-mg-10-kaplet",
    )
}