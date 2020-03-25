Insert into Pinguin_User (Name) Values
    ('Cyndy Beaudette'),
    ('Francene Murchison'),
    ('Nicolas Mignone'),
    ('Mitchell Slaten'),
    ('Gary Lomanto');

Insert into Issue (Id, Title, Description) values
    (1, 'Issue 1', 'Description of issue 1'),
    (2, 'Issue 2', 'Description of issue 2'),
    (3, 'Issue 3', 'Description of issue 3'),
    (4, 'Issue 4', 'Description of issue 4'),
    (5, 'Issue 5', 'Description of issue 5'),
    (6, 'Issue 6', 'Description of issue 6'),
    (7, 'Issue 7', 'Description of issue 7'),
    (8, 'Issue 8', 'Description of issue 8'),
    (9, 'Issue 9', 'Description of issue 9'),
    (10, 'Issue 10', 'Description of issue 10'),
    (11, 'Issue 11', 'Description of issue 11'),
    (12, 'Issue 12', 'Description of issue 12'),
    (13, 'Issue 13', 'Description of issue 13'),
    (14, 'Issue 14', 'Description of issue 14'),
    (15, 'Issue 15', 'Description of issue 15'),
    (16, 'Issue 16', 'Description of issue 16'),
    (17, 'Issue 17', 'Description of issue 17'),
    (18, 'Issue 18', 'Description of issue 18'),
    (19, 'Issue 19', 'Description of issue 19'),
    (20, 'Issue 20', 'Description of issue 20'),
    (21, 'Issue 21', 'Description of issue 21'),
    (22, 'Issue 22', 'Description of issue 22'),
    (23, 'Issue 23', 'Description of issue 23'),
    (24, 'Issue 24', 'Description of issue 24'),
    (25, 'Issue 25', 'Description of issue 25'),
    (26, 'Issue 26', 'Description of issue 26'),
    (27, 'Issue 27', 'Description of issue 27'),
    (28, 'Issue 28', 'Description of issue 28'),
    (29, 'Issue 29', 'Description of issue 29'),
    (30, 'Issue 30', 'Description of issue 30');

Insert into Story (Issue_Id, Status) values
    (1, 'New'),
    (2, 'New'),
    (3, 'New'),
    (4, 'Completed'),
    (5, 'Completed');

Insert into Bug (Issue_Id, Status, Priority) values
    (6, 'New', 'Minor'),
    (7, 'New', 'Major'),
    (8, 'Verified', 'Major'),
    (9, 'Resolved', 'Critical'),
    (10, 'Resolved', 'Critical');


Insert into Story (Issue_Id, Status, Estimate) values
    (11, 'Estimated', 2),
    (12, 'Estimated', 2),
    (13, 'Estimated', 4),
    (14, 'Estimated', 4),
    (15, 'Estimated', 4),
    (16, 'Estimated', 6),
    (17, 'Estimated', 6),
    (18, 'Estimated', 6),
    (19, 'Estimated', 8),
    (20, 'Estimated', 8),
    (21, 'Estimated', 8),
    (22, 'Estimated', 10),
    (23, 'Estimated', 10),
    (24, 'Estimated', 10),
    (25, 'Estimated', 10),
    (26, 'Estimated', 10),
    (27, 'Estimated', 10),
    (28, 'Estimated', 10),
    (29, 'Estimated', 10),
    (30, 'Estimated', 10);
