CREATE TABLE profile_availability (
                                      profile_id    INT        NOT NULL,
                                      availability_slot TEXT    NOT NULL,
                                      CONSTRAINT fk_profile_avail_profile
                                          FOREIGN KEY (profile_id)
                                              REFERENCES profiles(id)
                                              ON DELETE CASCADE
);
