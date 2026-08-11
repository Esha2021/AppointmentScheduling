-- Insert default roles
INSERT INTO role (name) VALUES ('USER') ON CONFLICT DO NOTHING;
INSERT INTO role (name) VALUES ('ADMIN') ON CONFLICT DO NOTHING;

-- Create a test schedule provider if it doesn't exist
INSERT INTO schedule_provider (schedule_provider_name, email_id, description)
SELECT 'Dr. Smith', 'dr.smith@hospital.com', 'General Practitioner'
WHERE NOT EXISTS (SELECT 1 FROM schedule_provider WHERE schedule_provider_name = 'Dr. Smith');
