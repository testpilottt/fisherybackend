INSERT INTO `members` (`member_id`, `first_name`, `hours_logged`, `last_name`, `password`, `profile_picture`, `username`, `access_level`)
VALUES ('1', 'Rohan', NULL, 'Aubeeluck', 'password', NULL, 'rohan', 'ADMIN'),
       ('2', 'Diksha', NULL, 'Aubeeluck', 'password', NULL, 'diksha',  'ADMIN');

INSERT INTO `country_setting` (`country_setting_id`, `thresh_hold_north_east`, `thresh_hold_north_west`, `thresh_hold_south_east`, `thresh_hold_south_west`, `country`, `latitude`, `longtitude`)
VALUES ('1', '90', '90', '80', '70', 'MAURITIUS', '-20.348404', '57.552152');