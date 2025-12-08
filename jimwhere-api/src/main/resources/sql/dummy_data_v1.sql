-- dummy_data.sql
CREATE DATABASE IF NOT EXISTS jim_where CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE jim_where;

DROP TABLE IF EXISTS room;
CREATE TABLE room (
  room_code BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  room_name VARCHAR(10) NOT NULL,
  room_width BIGINT NOT NULL,   -- 단위: cm
  room_length BIGINT NOT NULL,  -- 단위: cm
  room_height BIGINT NOT NULL,  -- 단위: cm
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NULL,
  user_code BIGINT NULL
);

-- A 타입 (작은방) : 300cm x 400cm x 250cm
INSERT INTO room (room_name, room_width, room_length, room_height, created_at) VALUES
('A1', 300, 400, 250, NOW()),
('A2', 300, 400, 250, NOW()),
('A3', 300, 400, 250, NOW()),
('A4', 300, 400, 250, NOW()),
('A5', 300, 400, 250, NOW());

-- B 타입 (중간방) : 400cm x 500cm x 300cm
INSERT INTO room (room_name, room_width, room_length, room_height, created_at) VALUES
('B1', 400, 500, 300, NOW()),
('B2', 400, 500, 300, NOW()),
('B3', 400, 500, 300, NOW()),
('B4', 400, 500, 300, NOW()),
('B5', 400, 500, 300, NOW());

-- C 타입 (큰방) : 600cm x 800cm x 350cm
INSERT INTO room (room_name, room_width, room_length, room_height, created_at) VALUES
('C1', 600, 800, 350, NOW()),
('C2', 600, 800, 350, NOW()),
('C3', 600, 800, 350, NOW()),
('C4', 600, 800, 350, NOW()),
('C5', 600, 800, 350, NOW());