package com.royma.asteroidradar.domain

import com.royma.asteroidradar.database.DatabaseAsteroid

/**
  * Asteroid objects used to make testing easier
  */
val TestAsteroid1 = DatabaseAsteroid(
    2159928,
    "Testeroid 1",
    "2022-08-03",
    18.05,
    1.4589485569,
    17.7559067373,
    0.2238852542,
    false
)

val TestAsteroid2 = DatabaseAsteroid(
    2445974,
    "Testeroid 2",
    "2022-07-19",
    20.29,
    0.5200438667,
    19.4510153897,
    0.3948717663,
    true
)

val TestAsteroid3 = DatabaseAsteroid(
    3290881,
    "Testeroid 3",
    "2022-03-16",
    20.2,
    0.5420507863,
    13.2887839991,
    0.109943137,
    true
)