-- Create customers
insert into
    CUSTOMER (customer_id, customer_name)
values
    (0, 'customer_1');

-- #1
insert into
    CUSTOMER (customer_id, customer_name)
values
    (1, 'customer_2');

-- #2
insert into
    CUSTOMER (customer_id, customer_name)
values
    (2, 'customer_3');

-- Create feature toggles
-- #1
insert into
    FEATURE_TOGGLE (
        feature_id,
        display_name,
        technical_name,
        expires_on,
        description,
        inverted,
        archived
    )
values
    (
        0,
        'Some feature One',
        'feature_1',
        { ts '2020-09-20 18:55:00' },
        'Some interesting feature which make you life easier.',
        0,
        0
    );

-- #2
insert into
    FEATURE_TOGGLE (
        feature_id,
        display_name,
        technical_name,
        expires_on,
        description,
        inverted,
        archived
    )
values
    (
        1,
        'Some feature Two',
        'feature_2',
        { ts '2020-07-20 18:55:00' },
        'One feature which expired :(',
        0,
        0
    );

-- #3
insert into
    FEATURE_TOGGLE (
        feature_id,
        display_name,
        technical_name,
        expires_on,
        description,
        inverted,
        archived
    )
values
    (
        2,
        'Some feature Three',
        'feature_3',
        { ts '2020-09-21 18:55:00' },
        'Another mock.',
        0,
        0
    );

-- Create links
-- #1
insert into
    CUSTOMER__FEATURE_TOGGLE (customer_id, feature_id)
values
    (0, 0);
-- #2
insert into
    CUSTOMER__FEATURE_TOGGLE (customer_id, feature_id)
values
    (0, 1);
-- #3
insert into
    CUSTOMER__FEATURE_TOGGLE (customer_id, feature_id)
values
    (0, 2);
-- #4
insert into
    CUSTOMER__FEATURE_TOGGLE (customer_id, feature_id)
values
    (1, 1);
-- #5
insert into
    CUSTOMER__FEATURE_TOGGLE (customer_id, feature_id)
values
    (1, 2);
-- #6
insert into
    CUSTOMER__FEATURE_TOGGLE (customer_id, feature_id)
values
    (2, 2);