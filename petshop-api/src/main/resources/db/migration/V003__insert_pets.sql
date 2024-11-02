USE petshop;
INSERT INTO pets (name, species, breed, birth_date, color, size, gender, castrated, weight, health_history, user_id, created_by, created_by_role, created_date)
VALUES
('Rex', 'Dog', 'Labrador', '2020-05-10', 'Black', 'Large', 'Male', TRUE, 30.5, 'Healthy', 3, 'Ester Helena Lívia Costa', 'CUSTOMER', '2024-11-01 11:20:18'),
('Bella', 'Cat', 'Siamese', '2019-08-15', 'White', 'Medium', 'Female', TRUE, 4.2, 'Healthy', 3, 'Ester Helena Lívia Costa', 'CUSTOMER', '2024-11-01 11:20:18'),
('Max', 'Dog', 'Beagle', '2021-01-20', 'Brown', 'Medium', 'Male', FALSE, 12.3, 'Healthy', 4, 'Kaique Fábio Ramos', 'CUSTOMER', '2024-11-02 14:59:50'),
('Luna', 'Cat', 'Persian', '2018-11-30', 'Gray', 'Small', 'Female', TRUE, 3.8, 'Healthy', 5, 'Cauã Bryan Castro', 'CUSTOMER', '2024-11-02 15:00:47'),
('Charlie', 'Dog', 'Poodle', '2022-03-25', 'White', 'Small', 'Male', TRUE, 5.0, 'Healthy', 5, 'Cauã Bryan Castro', 'CUSTOMER', '2024-11-02 15:00:47'),
('Milo', 'Rabbit', 'Holland Lop', '2021-07-14', 'Brown', 'Small', 'Male', FALSE, 1.5, 'Healthy', 6, 'Jennifer Aline Carolina Fogaça', 'CUSTOMER', '2024-11-02 15:02:03'),
('Simba', 'Cat', 'Maine Coon', '2020-09-05', 'Orange', 'Large', 'Male', TRUE, 6.7, 'Healthy', 7, 'Isabela Patrícia Marcela Figueiredo', 'CUSTOMER', '2024-11-02 15:02:57'),
('Buddy', 'Dog', 'Golden Retriever', '2019-12-12', 'Golden', 'Large', 'Male', TRUE, 28.0, 'Healthy', 8, 'Hugo Kevin Lucas Novaes', 'CUSTOMER', '2024-11-02 15:04:47'),
('Daisy', 'Bird', 'Parrot', '2020-04-22', 'Green', 'Small', 'Female', FALSE, 0.5, 'Healthy', 8, 'Hugo Kevin Lucas Novaes', 'CUSTOMER', '2024-11-02 15:04:47');