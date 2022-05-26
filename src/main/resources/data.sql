
INSERT INTO citizen (id, name, is_citizen, has_driving_license, children_count, parent_id) VALUES (123456, 'John Smith', true, true, 3, null);
INSERT INTO citizen (id, name, is_citizen, has_driving_license, children_count, parent_id) VALUES (123457, 'Mike Smith', true, true, 0, 123456);
INSERT INTO citizen (id, name, is_citizen, has_driving_license, children_count, parent_id) VALUES (123458, 'Jessica Smith', true, true, 0, 123456);
INSERT INTO citizen (id, name, is_citizen, has_driving_license, children_count, parent_id) VALUES (123459, 'Sarah Smith', true, true, 0, 123456);

INSERT INTO citizen (id, name, is_citizen, has_driving_license, children_count, parent_id) VALUES (123461, 'Michael Tall', false, true, 0, null);
INSERT INTO citizen (id, name, is_citizen, has_driving_license, children_count, parent_id) VALUES (123462, 'Joe Bloggs', false, true, 1, null);
INSERT INTO citizen (id, name, is_citizen, has_driving_license, children_count, parent_id) VALUES (123463, 'Sarah Bloggs', false, true, 0, 123462);

