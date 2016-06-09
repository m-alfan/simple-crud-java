CREATE TABLE `alfan` (
  `id` int(11) NOT NULL,
  `nama` varchar(45) NOT NULL,
  `npm` varchar(15) NOT NULL,
  `alamat` text NOT NULL,
  `agama` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `alfan`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `alfan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;