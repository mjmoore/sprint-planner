Insert into PinguinUser (Id, Name) Values
    (51, 'Cyndy Beaudette'),
    (52, 'Francene Murchison'),
    (53, 'Nicolas Mignone'),
    (54, 'Mitchell Slaten'),
    (55, 'Gary Lomanto');

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
    (10, 'Issue 10', 'Description of issue 10');

Insert into Story (Id, IssueId, Status) values
    (11, 1, 'New'),
    (12, 2, 'New'),
    (13, 3, 'New'),
    (14, 4, 'Completed'),
    (15, 5, 'Completed');

Insert into Bug (Id, IssueId, Status, Priority) values
    (16, 6, 'New', 'Major'),
    (17, 7, 'New', 'Major'),
    (18, 8, 'New', 'Major'),
    (19, 9, 'New', 'Major'),
    (20, 10, 'New', 'Major');
