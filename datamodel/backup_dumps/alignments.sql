SELECT * FROM salesfast.alignments;

SELECT * FROM salesfast.alignments al 
WHERE al.userId = 1 AND NOT EXISTS (
SELECT 1 from salesfast.appointment app
WHERE app.physicianId = al.physicianId
AND app.userId = al.userId
AND app.productId = al.productId);

SELECT * FROM salesfast.alignments 
WHERE alignments.userId = 1 AND alignments.zip in ('11001', '11006')
AND NOT EXISTS (
SELECT 1 from salesfast.appointment WHERE 
appointment.physicianId = alignments.physicianId AND
appointment.userId = alignments.userId AND
appointment.productId = alignments.productId);

SELECT * FROM salesfast.alignments WHERE alignments.userId = 1 AND alignments.zip IN 
(SELECT appointment.zip FROM salesfast.appointment WHERE appointment.userId = 1 GROUP BY zip)
AND NOT EXISTS (SELECT 1 from salesfast.appointment WHERE
alignments.physicianId = appointment.physicianId AND
alignments.userId = appointment.userId AND
alignments.productId = appointment.productId);

