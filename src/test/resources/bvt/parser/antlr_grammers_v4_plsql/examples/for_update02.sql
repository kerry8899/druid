select employee_id from (select employee_id+1 as employee_id from employees)
   for update


