CREATE VIEW decora as 
select 
senateur_id, 
string_agg(type || COALESCE(', ' || grade,''),', ' order by id) as decora
FROM decoration
group by senateur_id
