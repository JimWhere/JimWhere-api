-- Seed-only SQL (DDL removed)
-- Schema (tables) should be created by Hibernate or a migration tool (Flyway/Liquibase).
-- If you need to run this file manually against the database, ensure the database and tables exist and uncomment the USE statement below.
USE jim_where;

-- A 타입 (작은방) : 3000cm x 4000cm x 2500cm
INSERT INTO room (room_name, room_width, room_length, room_height, created_at) VALUES
('A1', 3000, 4000, 2500, NOW()),
('A2', 3000, 4000, 2500, NOW()),
('A3', 3000, 4000, 2500, NOW()),
('A4', 3000, 4000, 2500, NOW()),
('A5', 3000, 4000, 2500, NOW());

-- B 타입 (중간방) : 4000cm x 5000cm x 3000cm
INSERT INTO room (room_name, room_width, room_length, room_height, created_at) VALUES
('B1', 4000, 5000, 3000, NOW()),
('B2', 4000, 5000, 3000, NOW()),
('B3', 4000, 5000, 3000, NOW()),
('B4', 4000, 5000, 3000, NOW()),
('B5', 4000, 5000, 3000, NOW());

-- C 타입 (큰방) : 6000cm x 8000cm x 3500cm
INSERT INTO room (room_name, room_width, room_length, room_height, created_at) VALUES
('C1', 6000, 8000, 3500, NOW()),
('C2', 6000, 8000, 3500, NOW()),
('C3', 6000, 8000, 3500, NOW()),
('C4', 6000, 8000, 3500, NOW()),
('C5', 6000, 8000, 3500, NOW());

-- Sample boxes: assign boxes per room type with content/counts (A:16, B:8, C:4)
-- A-type (a1..a16) distributed round-robin to room_code 1..5
INSERT INTO box (box_name, box_possible_status, box_content, box_current_count, box_width, box_length, box_height, created_at, room_code) VALUES
('a1', 'Y', NULL, 70, 80, 100, 60, NOW(), 1),
('a2', 'Y', NULL, 80, 80, 100, 60, NOW(), 2),
('a3', 'N', '은반지', 50, 80, 100, 60, NOW(), 3),
('a4', 'N', '금반지', 40, 80, 100, 60, NOW(), 4),
('b1', 'N', '무테팔찌', 34, 100, 120, 80, NOW(), 5),
('b2', 'N', '체인팔찌', 20, 100, 120, 80, NOW(), 1),
('b3', 'N', '이어링', 14, 100, 120, 80, NOW(), 2),
('b4', 'N', '드롭이어링', 16, 100, 120, 80, NOW(), 3),
('c1', 'Y', NULL, 10, 150, 200, 120, NOW(), 4),
('c2', 'Y', NULL, 5, 150, 200, 120, NOW(), 5),
('c3', 'N', '은반지', 25, 150, 200, 120, NOW(), 1),
('c4', 'N', '금반지', 12, 150, 200, 120, NOW(), 2),
('d1', 'N', '무테팔찌', 7, 80, 100, 60, NOW(), 3),
('d2', 'N', '체인팔찌', 3, 80, 100, 60, NOW(), 4),
('d3', 'N', '이어링', 2, 80, 100, 60, NOW(), 5),
('d4', 'N', '드롭이어링', 1, 80, 100, 60, NOW(), 1),

-- B-type (b1..b8) distributed to room_code 6..10
('a1', 'Y', NULL, 4, 80, 100, 60, NOW(), 6),
('a2', 'Y', NULL, 3, 80, 100, 60, NOW(), 7),
('a3', 'Y', '책상다리', 12, 80, 100, 60, NOW(), 8),
('a4', 'Y', '책꽂이', 5, 80, 100, 60, NOW(), 9),
('b1', 'Y', NULL, 8, 100, 120, 80, NOW(), 10),
('b2', 'Y', NULL, 6, 100, 120, 80, NOW(), 6),
('b3', 'Y', '의자다리', 12, 100, 120, 80, NOW(), 7),
('b4', 'Y', '책상프레임', 2, 100, 120, 80, NOW(), 8),

-- C-type (c1..c4) distributed to room_code 11..14 (larger boxes, mostly empty)
('a1', 'Y', NULL, 0, 80, 100, 60, NOW(), 11),
('a2', 'Y', NULL, 0, 80, 100, 60, NOW(), 12),
('b1', 'Y', NULL, 0, 100, 120, 80, NOW(), 13),
('b2', 'Y', NULL, 0, 100, 120, 80, NOW(), 14);

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