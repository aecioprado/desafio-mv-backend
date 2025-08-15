-- =============================================
-- Script: data.sql
-- Description: Sample data for breakfast tracking system
-- Records: 5 employees with their breakfast items
-- =============================================

-- ========================================
-- BREAKFAST RECORDS (Employees)
-- ========================================

-- Insert breakfast records for 5 employees
INSERT INTO breakfast (employee_name, social_security_number) VALUES
('John Smith', '123-45-6789'),
('Maria Garcia', '234-56-7890'),
('David Johnson', '345-67-8901'),
('Sarah Wilson', '456-78-9012'),
('Michael Brown', '567-89-0123');

-- ========================================
-- BREAKFAST ITEMS
-- ========================================

-- John Smith's breakfast items (id=1)
INSERT INTO breakfast_item (name, breakfast_id) VALUES
('Scrambled Eggs', 1),
('Bacon', 1),
('Orange Juice', 1),
('Toast', 1);

-- Maria Garcia's breakfast items (id=2)
INSERT INTO breakfast_item (name, breakfast_id) VALUES
('Oatmeal', 2),
('Fresh Berries', 2),
('Coffee', 2),
('Greek Yogurt', 2);

-- David Johnson's breakfast items (id=3)
INSERT INTO breakfast_item (name, breakfast_id) VALUES
('Pancakes', 3),
('Maple Syrup', 3),
('Sausage Links', 3),
('Apple Juice', 3),
('Butter', 3);

-- Sarah Wilson's breakfast items (id=4)
INSERT INTO breakfast_item (name, breakfast_id) VALUES
('Avocado Toast', 4),
('Green Tea', 4),
('Fresh Fruit Salad', 4);

-- Michael Brown's breakfast items (id=5)
INSERT INTO breakfast_item (name, breakfast_id) VALUES
('Cereal', 5),
('Milk', 5),
('Banana', 5),
('Coffee', 5),
('Orange Slices', 5),
('Granola', 5);