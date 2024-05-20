package com.soulcode.projetofinal.constants;

import com.soulcode.projetofinal.models.Administrato;
import com.soulcode.projetofinal.models.Department;
import com.soulcode.projetofinal.models.Person;
import com.soulcode.projetofinal.models.Priority;
import com.soulcode.projetofinal.models.Status;
import com.soulcode.projetofinal.models.Type;

import java.time.LocalDateTime;


public class Constants {
    public static final Administrato ADMINISTRATO = new Administrato("John Doe", "john@example.com", "password", null);
    public static final Department DEPARTMENT = new Department("IT");
    public static final Department DEPARTMENT2 = new Department("HR");
}