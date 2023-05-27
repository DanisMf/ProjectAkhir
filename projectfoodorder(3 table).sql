-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 25 Bulan Mei 2023 pada 07.37
-- Versi server: 10.4.28-MariaDB
-- Versi PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `projectfoodorder`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `datapembelian`
--

CREATE TABLE `datapembelian` (
  `kode_transaksi` varchar(50) NOT NULL,
  `id_user` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `datapembelian`
--

INSERT INTO `datapembelian` (`kode_transaksi`, `id_user`) VALUES
('KT-001', 'ID-001'),
('KT-002', 'ID-002');

-- --------------------------------------------------------

--
-- Struktur dari tabel `datatransaksi`
--

CREATE TABLE `datatransaksi` (
  `kode_transaksi` varchar(50) NOT NULL,
  `nama_pesanan` varchar(50) NOT NULL,
  `quantity` int(10) NOT NULL,
  `total_harga` bigint(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `datatransaksi`
--

INSERT INTO `datatransaksi` (`kode_transaksi`, `nama_pesanan`, `quantity`, `total_harga`) VALUES
('KT-001', 'Chicken burger', 2, 40000),
('KT-001', 'Cheese fries', 2, 30000),
('KT-001', 'Paincake', 2, 30000),
('KT-001', 'Chicken nuggets', 2, 30000),
('KT-001', 'Sweet potato', 2, 30000),
('KT-001', 'Paincake', 2, 30000),
('KT-001', 'Chicken nuggets', 2, 30000),
('KT-002', 'Air Mineral Botol', 2, 10000),
('KT-002', 'Es Teh Manis', 2, 10000),
('KT-002', 'Es Podeng', 2, 10000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `datauser`
--

CREATE TABLE `datauser` (
  `id_user` varchar(50) NOT NULL,
  `name` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(50) NOT NULL,
  `phone_number` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `datauser`
--

INSERT INTO `datauser` (`id_user`, `name`, `username`, `password`, `phone_number`) VALUES
('ID-001', 'Muhammad Ramadhan Prinada', 'muhammad.prinada', '12345678', '085839268065'),
('ID-002', 'Wily Ahmad Fauzan', 'wily.ahmad', '12345678', '081745698745'),
('ID-003', 'Rafi Daniswara', 'rafi.danis', '12345678', '087775468975'),
('ID-004', 'Muhammad Taufiqul Hakim Maha', 'king.taufiq', '12345678', '089963258741'),
('ID-005', 'Rafly Erlyan Wimbu', 'Rafly.wimbu', '12345678', '087745963201'),
('ID-006', 'Muhammad Rafi', 'muhammad.rafi', '12345678', '085893268065');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `datapembelian`
--
ALTER TABLE `datapembelian`
  ADD PRIMARY KEY (`kode_transaksi`),
  ADD KEY `id_user_foreign_key` (`id_user`);

--
-- Indeks untuk tabel `datatransaksi`
--
ALTER TABLE `datatransaksi`
  ADD KEY `kode_transaksi_foreign_key` (`kode_transaksi`);

--
-- Indeks untuk tabel `datauser`
--
ALTER TABLE `datauser`
  ADD PRIMARY KEY (`id_user`);

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `datapembelian`
--
ALTER TABLE `datapembelian`
  ADD CONSTRAINT `id_user_foreign_key` FOREIGN KEY (`id_user`) REFERENCES `datauser` (`id_user`);

--
-- Ketidakleluasaan untuk tabel `datatransaksi`
--
ALTER TABLE `datatransaksi`
  ADD CONSTRAINT `kode_transaksi_foreign_key` FOREIGN KEY (`kode_transaksi`) REFERENCES `datapembelian` (`kode_transaksi`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
