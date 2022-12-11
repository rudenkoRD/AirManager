insert into "flights" (start_airport_id, arrival_airport_id, start_time, end_time, first_pilot_id, second_pilot_id,
                       plane_id)
values (1, 2, now() - interval '1 day', now() + interval '1 day', 1, 2, 1), --now
       (2, 3, now() + interval '1 day', now() + interval '2 day', 1, 2, 1), --future
       (1, 3, now() - interval '2 day', now() - interval '1 day', 1, 2, 1); --past