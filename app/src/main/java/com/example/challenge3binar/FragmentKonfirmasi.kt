package com.example.challenge3binar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentKonfirmasi.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentKonfirmasi : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var dataCartAdapter: DataCartAdapter
    private lateinit var dataCartDao: CartDao
    private lateinit var orderDao: OrderDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_konfirmasi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById((R.id.recyclerView))
        recyclerView.layoutManager = LinearLayoutManager(context)
        dataCartDao = DatabaseCart.getInstance(requireContext()).simpleChartDao
        orderDao = DatabaseCart.getInstance(requireContext()).orderDao

        dataCartAdapter = DataCartAdapter(requireContext(), dataCartDao)
        recyclerView.adapter = dataCartAdapter

        // Inisialisasi database
        val database = DatabaseCart.getInstance(requireContext())
        val dataCartDao = database.simpleChartDao

        // Mengamati perubahan data dari database dan memperbarui adapter
        dataCartDao.getAllItem().observe(viewLifecycleOwner, Observer { dataCartList ->
            dataCartAdapter.setDataCartList(dataCartList)

            // Hitung total harga dari dataCartList
            var totalHarga = 0
            for (item in dataCartList) {
                val itemTotalPrice = item.itemPrice?.times(item.itemQuantity) ?: 0
                totalHarga += itemTotalPrice
            }

            // Tampilkan total harga di TextView
            val totalHargaTextView: TextView = view.findViewById(R.id.tv_ringkasanPembayaran)
            totalHargaTextView.text = "Total Harga = Rp. ${totalHarga}"
            val btnPesanBerhasil: Button = view.findViewById(R.id.btn_pesananBerhasil)
            // Tambahkan kondisi jika dataCartList tidak kosong
            if (dataCartList.isNotEmpty()) {
                // Tombol untuk berpindah ke FragmentHome
                btnPesanBerhasil.setOnClickListener {
                    // Simpan data pesanan ke database pesanan
                    val pesananList = dataCartAdapter.getDataCartList()
                    for (item in pesananList) {
                        val orderData = OrderData(
                            itemName = item.itemName,
                            itemImage = item.itemImage,
                            itemPrice = item.itemPrice,
                            itemQuantity = item.itemQuantity
                        )
                        orderDao.insert(orderData)
                    }

                    // Hapus semua item di keranjang setelah pesanan berhasil
                    dataCartDao.deleteAllItems()

                    // Tampilkan pesan "Pesanan Anda Berhasil"
                    Toast.makeText(requireContext(), "Pesanan Anda Berhasil", Toast.LENGTH_SHORT).show()

                    // Navigasi ke FragmentHome
                    findNavController().navigate(R.id.action_fragmentKonfirmasi_to_fragmentHome)
                }
            } else {
                // Jika dataCartList kosong, nonaktifkan tombol atau berikan pesan kepada pengguna
                btnPesanBerhasil.isEnabled = false
                btnPesanBerhasil.text = "Cart Is Empty"
            }
        })


    }

    companion object {

    }
}