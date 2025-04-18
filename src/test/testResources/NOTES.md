2024s2 OOSE Assignment 1 -- Faulty Test Data
============================================

Use these files to see if your application gracefully handles erroneous external input. Most of these files were created by inserting a single, specific error into an otherwise valid input file.

These files don't represent all possible external errors, just a representative sample.


File                   | Error
---------------------- | ----------------------------------
empty_grid             | starts with `0,1`
invalid_dimensions1    | starts with `12;10`
invalid_dimensions1    | starts with `twelve,ten`
missing_records        | contains fewer lines than required
invalid_terrain        | contains `rockyish`
invalid_zoning_rule    | contains `flat,swampy=1`
invalid_heritage       | contains `heritage=acrylic`
invalid_flood_risk1    | contains `flood-risk=101.1`
invalid_flood_risk2    | contains `flood-risk=high`
invalid_contamination  | contains `contamination=0`
invalid_height_limit1  | contains `height-limit=0`
invalid_height_limit2  | contains `height-limit=25.5`
