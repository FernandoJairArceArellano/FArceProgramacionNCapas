package com.Digis01.FArceProgramacionNCapas.RestController;

import com.Digis01.FArceProgramacionNCapas.ML.OperacionRequest;
import com.Digis01.FArceProgramacionNCapas.ML.Operacion;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demoapi")
public class DemoRestController {

    @GetMapping("saludo")
    public String Saludo() {
        return "Hola Mundo";
    }

    // Suma usando PathVariable
    @GetMapping("/sumar/{a}/{b}")
    public ResponseEntity<Operacion> sumar(@PathVariable int a, @PathVariable int b) {
        Operacion op = new Operacion();
        op.setTipo("Suma");
        op.setOperando1(a);
        op.setOperando2(b);
        op.setResultado(a + " + " + b + " = " + (a + b));
        return ResponseEntity.ok(op);
    }
    // consumirlo localhost:8080/demoapi/sumar/a/b    

    // Resta usuando RequestParam
    @GetMapping("/restar")
    public ResponseEntity<Operacion> restar(@RequestParam int a, @RequestParam int b) {
        Operacion op = new Operacion();
        op.setTipo("Resta");
        op.setOperando1(a);
        op.setOperando2(b);
        op.setResultado(a + " - " + b + " = " + (a + b));
        return ResponseEntity.ok(op);
    }
    // consumirlo localhost:8080/demoapi/restar?a=a&b=b

    // Multiplicación usando PathVariable
    @GetMapping("/multiplicar/{a}/{b}")
    public ResponseEntity<Operacion> multiplicar(@PathVariable int a, @PathVariable int b) {
        Operacion op = new Operacion();
        op.setTipo("Multiplicación");
        op.setOperando1(a);
        op.setOperando2(b);
        op.setResultado(a + " * " + b + " = " + (a * b));
        return ResponseEntity.ok(op);
    }
    // consumirlo localhost:8080/demoapi/multiplicar/a/b

    // División usando RequestParam
    @GetMapping("/dividir")
    public ResponseEntity<?> dividir(@RequestParam int a, @RequestParam int b) {
        if (b == 0) {
            return ResponseEntity.badRequest().body("Error: No se puede dividir entre cero.");
        }
        Operacion op = new Operacion();
        op.setTipo("División");
        op.setOperando1(a);
        op.setOperando2(b);
        op.setResultado(a + " / " + b + " = " + ((double) a / b));
        return ResponseEntity.ok(op);
    }
    // consumirlo localhost:8080/demoapi/divir?a=a&b=b

    // Suma usando RequestParam
    @GetMapping("/sumar2")
    public ResponseEntity<Operacion> sumar2(@RequestParam List<Integer> numeros) {
        int listaSuma = 0;

        for (Integer numero : numeros) {
            listaSuma += numero;
        }

        Operacion op = new Operacion();
        op.setTipo("Suma una lista de numeros");
        op.setResultado("Suma de " + numeros + " = " + listaSuma);
        return ResponseEntity.ok(op);
    }
    // consumirlo localhost:8080/demoapi/sumar2?numeros=n1,n2,n3,n4.....

    // Suma que recibe una lista de numero por JSON body
    @PostMapping("/sumarLista")
    public ResponseEntity<Operacion> sumarListaBody(@RequestBody OperacionRequest request) {
        int suma = 0;
        for (Integer num : request.getNumeros()) {
            suma += num;
        }

        Operacion op = new Operacion();
        op.setTipo("Suma desde el body");
        op.setResultado("Suma de " + request.getNumeros() + " = " + suma);
        return ResponseEntity.ok(op);
    }

}
