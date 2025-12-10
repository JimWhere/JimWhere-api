-- Seed-only SQL (DDL removed)
-- Schema (tables) should be created by Hibernate or a migration tool (Flyway/Liquibase).
-- If you need to run this file manually against the database, ensure the database and tables exist and uncomment the USE statement below.
USE jim_where;

-- A 타입 (작은방) : 3000cm x 4000cm x 2500cm
INSERT INTO room (user_code, room_name, room_width, room_length, room_height, created_at, updated_at) VALUES
(1, 'A1', 3000, 4000, 2500, NOW(), NOW()),
(2, 'A2', 3000, 4000, 2500, NOW(), NOW()),
(3, 'A3', 3000, 4000, 2500, NOW(), NOW()),
(4, 'A4', 3000, 4000, 2500, NOW(), NOW()),
(5, 'A5', 3000, 4000, 2500, NOW(), NOW());

-- B 타입 (중간방) : 4000cm x 5000cm x 3000cm
INSERT INTO room (user_code, room_name, room_width, room_length, room_height, created_at, updated_at) VALUES
(6, 'B1', 4000, 5000, 3000, NOW(), NOW()),
(7, 'B2', 4000, 5000, 3000, NOW(), NOW()),
(8, 'B3', 4000, 5000, 3000, NOW(), NOW()),
(9, 'B4', 4000, 5000, 3000, NOW(), NOW()),
(10, 'B5', 4000, 5000, 3000, NOW(), NOW());

-- C 타입 (큰방) : 6000cm x 8000cm x 3500cm
INSERT INTO room (user_code, room_name, room_width, room_length, room_height, created_at, updated_at) VALUES
(1, 'C1', 6000, 8000, 3500, NOW(), NOW()),
(2, 'C2', 6000, 8000, 3500, NOW(), NOW()),
(3, 'C3', 6000, 8000, 3500, NOW(), NOW()),
(4, 'C4', 6000, 8000, 3500, NOW(), NOW()),
(5, 'C5', 6000, 8000, 3500, NOW(), NOW());

-- Sample boxes: assign boxes per room type with content/counts (A:16, B:8, C:4)
-- A-type (a1..a16) distributed round-robin to room_code 1..5
INSERT INTO box (box_name, box_possible_status, box_content, box_current_count, box_width, box_length, box_height, created_at, updated_at, room_code) VALUES
('a1', 'Y', NULL, 70, 100, 100, 100, NOW(), NULL, 1),
('a2', 'Y', NULL, 80, 100, 100, 100, NOW(), NULL, 2),
('a3', 'N', '은반지', 50, 100, 100, 100, NOW(), NOW(), 3),
('a4', 'N', '금반지', 40, 100, 100, 100, NOW(), NOW(), 4),
('a5', 'N', '무테팔찌', 34, 100, 100, 100, NOW(), NOW(), 5),
('a6', 'N', '체인팔찌', 20, 100, 100, 100, NOW(), NOW(), 1),
('a7', 'N', '이어링', 14, 100, 100, 100, NOW(), NOW(), 2),
('a8', 'N', '드롭이어링', 16, 100, 100, 100, NOW(), NOW(), 3),
('a9', 'Y', NULL, 10, 100, 100, 100, NOW(), NULL, 4),
('a10', 'Y', NULL, 5, 100, 100, 100, NOW(), NULL, 5),
('a11', 'N', '은반지', 25, 100, 100, 100, NOW(), NOW(), 1),
('a12', 'N', '금반지', 12, 100, 100, 100, NOW(), NOW(), 2),
('a13', 'N', '무테팔찌', 7, 100, 100, 100, NOW(), NOW(), 3),
('a14', 'N', '체인팔찌', 3, 100, 100, 100, NOW(), NOW(), 4),
('a15', 'N', '이어링', 2, 100, 100, 100, NOW(), NOW(), 5),
('a16', 'N', '드롭이어링', 1, 100, 100, 100, NOW(), NOW(), 1),

-- B-type (b1..b8) distributed to room_code 6..10
('b1', 'Y', NULL, 4, 70, 70, 70, NOW(), NULL, 6),
('b2', 'Y', NULL, 3, 70, 70, 70, NOW(), NULL, 7),
('b3', 'Y', '책상다리', 12, 70, 70, 70, NOW(), NOW(), 8),
('b4', 'Y', '책꽂이', 5, 70, 70, 70, NOW(), NOW(), 9),
('b5', 'Y', NULL, 8, 70, 70, 70, NOW(), NULL, 10),
('b6', 'Y', NULL, 6, 70, 70, 70, NOW(), NULL, 6),
('b7', 'Y', '의자다리', 12, 70, 70, 70, NOW(), NOW(), 7),
('b8', 'Y', '책상프레임', 2, 70, 70, 70, NOW(), NOW(), 8),

-- C-type (c1..c4) distributed to room_code 11..14 (larger boxes, mostly empty)
('c1', 'Y', NULL, 5, 50, 50, 50, NOW(), NULL, 11),
('c2', 'Y', NULL, 7, 50, 50, 50, NOW(), NULL, 12),
('c3', 'Y', NULL, 3, 50, 50, 50, NOW(), NULL, 13),
('c4', 'Y', NULL, 8, 50, 50, 50, NOW(), NULL, 14);

-- Dynamically set box dimensions (cm) based on room type (room_name prefix A/B/C)
-- Adjust these values if you want different default box sizes per room type.
UPDATE box b
JOIN room r ON b.room_code = r.room_code
SET
  b.box_width = CASE
    WHEN LEFT(r.room_name, 1) = 'A' THEN 80
    WHEN LEFT(r.room_name, 1) = 'B' THEN 100
    WHEN LEFT(r.room_name, 1) = 'C' THEN 150
    ELSE 90
  END,
  b.box_length = CASE
    WHEN LEFT(r.room_name, 1) = 'A' THEN 100
    WHEN LEFT(r.room_name, 1) = 'B' THEN 120
    WHEN LEFT(r.room_name, 1) = 'C' THEN 200
    ELSE 110
  END,
  b.box_height = CASE
    WHEN LEFT(r.room_name, 1) = 'A' THEN 60
    WHEN LEFT(r.room_name, 1) = 'B' THEN 80
    WHEN LEFT(r.room_name, 1) = 'C' THEN 120
    ELSE 70
  END
WHERE b.box_width IS NULL OR b.box_length IS NULL OR b.box_height IS NULL;

-- Optional: verify updates (uncomment to run)
-- SELECT b.box_code, r.room_name, b.box_width, b.box_length, b.box_height FROM box b JOIN room r ON b.room_code = r.room_code ORDER BY b.box_code;