-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 29 Sty 2024, 20:11
-- Wersja serwera: 10.4.21-MariaDB
-- Wersja PHP: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `hyplayer_db`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `albums`
--

CREATE TABLE `albums` (
  `album_id` int(11) NOT NULL,
  `album_title` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `albums`
--

INSERT INTO `albums` (`album_id`, `album_title`) VALUES
(1, 'Ultra'),
(2, 'Czempion'),
(3, 'Adwokat Diabla'),
(4, 'brak'),
(5, 'Astroworld');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `authors`
--

CREATE TABLE `authors` (
  `author_id` int(11) NOT NULL,
  `name` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `authors`
--

INSERT INTO `authors` (`author_id`, `name`) VALUES
(1, 'Kizo'),
(2, 'Malik Montana'),
(3, 'Zeamsone'),
(4, 'Figo i Samogony'),
(5, 'Travis Scott'),
(6, 'sanah'),
(7, 'Lanberry'),
(8, 'Smolasty'),
(9, 'Blacha 2115'),
(10, 'PUSHER'),
(11, 'Young Leosia'),
(12, 'Sylwia Grzeszczak'),
(13, 'Nikos'),
(14, 'Szpaku'),
(15, 'Stumblin\' In'),
(16, 'Dua Lipa');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `authors_songs`
--

CREATE TABLE `authors_songs` (
  `authors_id` int(11) DEFAULT NULL,
  `songs_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `authors_songs`
--

INSERT INTO `authors_songs` (`authors_id`, `songs_id`) VALUES
(1, 1),
(2, 3),
(2, 5),
(4, 5),
(1, 1),
(1, 1),
(1, 1),
(1, 1),
(1, 1),
(1, 1),
(1, 1),
(1, 1),
(1, 1),
(1, 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `personal_data`
--

CREATE TABLE `personal_data` (
  `user_id` int(11) NOT NULL,
  `name` text NOT NULL,
  `surname` text NOT NULL,
  `address` text NOT NULL,
  `city` text NOT NULL,
  `postcode` text NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `songs`
--

CREATE TABLE `songs` (
  `song_id` int(11) NOT NULL,
  `song_title` text DEFAULT NULL,
  `album_id` int(11) DEFAULT NULL,
  `price` float NOT NULL,
  `image_url` varchar(100) NOT NULL,
  `genre` text NOT NULL,
  `mp3_url` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `songs`
--

INSERT INTO `songs` (`song_id`, `song_title`, `album_id`, `price`, `image_url`, `genre`, `mp3_url`) VALUES
(1, 'ULTRA', 1, 10, '/songsImages/1.png', 'Kizomania', '\"\\songFiles\\Piosenki mp3\\Kizo - ULTRA.mp3\"'),
(2, 'CZEMPION', 2, 21, '/songsImages/2.png', '', '\"\\songFiles\\Kizo - CZEMPION.mp3\"'),
(3, 'Jetlag', 3, 421, '/songsImages/3.png', '', '\"\\songFiles\\MalikMontana - Jetlag.mp3\"'),
(4, '5 INFLUENCEREK', 4, 12, '/songsImages/4.png', '', '\"\\songFiles\\Zeamsone - 5 INFLUENCEREK.mp3\"'),
(5, 'ErotycznePif-Paf', 4, 22, '/songsImages/5.png', 'Pop', '\"\\songFiles\\Erotyczne Pif-Paf.mp3\"'),
(6, 'SICKO MODE', 4, 23, '/songsImages/6.png', '', '\"\\songFiles\\Travis Scott - SICKO MODE.mp3\"'),
(7, 'goosebumps', 4, 15, '/songsImages/7.png', '', '\"\\songFiles\\Travis Scott - goosebumps.mp3\"'),
(8, 'Jestem Twoja Bajka', 4, 62, '/songsImages/8.png', '', '\"\\songFiles\\sanah – Jestem Twoja Bajka.mp3\"'),
(9, 'Dzieki, że jestes', 4, 83, '/songsImages/9.png', '', '\"\\songFiles\\Lanberry- Dzięki, że jesteś photo.png\"'),
(10, 'Nim Zajdzie Slonce', 4, 72, '/songsImages/10.png', '', '\"\\songFiles\\Smolasty - Nim Zajdzie Słonce.mp3\"'),
(11, 'Kevin sam w domu', 4, 30, '/songsImages/11.png', '', '\"\\songFiles\\\\Blacha 2115 - Kevin sam w domu.mp3\"'),
(12, 'LEJE WINA', 4, 32, '/songsImages/12.png', '', '\"\\songFiles\\PUSHER - LEJE WINA.mp3\"'),
(13, 'BFF', 4, 12, '/songsImages/13.png', '', '\"\\songFiles\\BFF  Young Leosia.mp3\"'),
(14, 'Motyle', 4, 1, '/songsImages/14.png', '', '\"\\songFiles\\Sylwia Grzeszczak - Motyle.mp3\"'),
(15, 'Hipnoza', 4, 12, '/songsImages/15.png', '', '\"\\songFiles\\NIKOŚ - HIPNOZA.mp3\"'),
(16, 'Plaster', 4, 29, '/songsImages/16.png', '', '\"\\songFiles\\Szpaku - Aniol Stroz.mp3\"'),
(17, 'Garfield ', 4, 11, '/songsImages/17.png', '', '\"\\songFiles\\Szpaku - Garfield.mp3\"'),
(18, 'Aniol stroz', 4, 12, '/songsImages/18.png', '', '\"\\songFiles\\Szpaku - Aniol Stroz.mp3\"'),
(19, 'CYRIL', 4, 90, '/songsImages/19.png', '', '\"\\songFiles\\CYRIL - Stumblin\' In.mp3\"'),
(20, 'Houdini', 4, 50, '/songsImages/20.png', '', '\"\\songFiles\\Dua Lipa - Houdini.mp3\"');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `transactions`
--

CREATE TABLE `transactions` (
  `transaction_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `song_id` int(11) DEFAULT NULL,
  `price` float(10,0) DEFAULT NULL,
  `data` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` text DEFAULT NULL,
  `email` text DEFAULT NULL,
  `password` text DEFAULT NULL,
  `user_type` tinyint(1) DEFAULT NULL,
  `balance` float DEFAULT NULL,
  `orginal_admin` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `albums`
--
ALTER TABLE `albums`
  ADD PRIMARY KEY (`album_id`);

--
-- Indeksy dla tabeli `authors`
--
ALTER TABLE `authors`
  ADD PRIMARY KEY (`author_id`);

--
-- Indeksy dla tabeli `authors_songs`
--
ALTER TABLE `authors_songs`
  ADD KEY `authors_id` (`authors_id`),
  ADD KEY `songs_id` (`songs_id`);

--
-- Indeksy dla tabeli `personal_data`
--
ALTER TABLE `personal_data`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indeksy dla tabeli `songs`
--
ALTER TABLE `songs`
  ADD PRIMARY KEY (`song_id`),
  ADD KEY `album_id` (`album_id`);

--
-- Indeksy dla tabeli `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`transaction_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `song_id` (`song_id`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `albums`
--
ALTER TABLE `albums`
  MODIFY `album_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT dla tabeli `authors`
--
ALTER TABLE `authors`
  MODIFY `author_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT dla tabeli `personal_data`
--
ALTER TABLE `personal_data`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT dla tabeli `songs`
--
ALTER TABLE `songs`
  MODIFY `song_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT dla tabeli `transactions`
--
ALTER TABLE `transactions`
  MODIFY `transaction_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `authors_songs`
--
ALTER TABLE `authors_songs`
  ADD CONSTRAINT `authors_songs_ibfk_1` FOREIGN KEY (`authors_id`) REFERENCES `authors` (`author_id`),
  ADD CONSTRAINT `authors_songs_ibfk_2` FOREIGN KEY (`songs_id`) REFERENCES `songs` (`song_id`);

--
-- Ograniczenia dla tabeli `personal_data`
--
ALTER TABLE `personal_data`
  ADD CONSTRAINT `personal_data_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Ograniczenia dla tabeli `songs`
--
ALTER TABLE `songs`
  ADD CONSTRAINT `songs_ibfk_2` FOREIGN KEY (`album_id`) REFERENCES `albums` (`album_id`);

--
-- Ograniczenia dla tabeli `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `transactions_ibfk_2` FOREIGN KEY (`song_id`) REFERENCES `songs` (`song_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
