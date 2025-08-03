package models;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RefBancariaModel {
    private final File refBancariasFile;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public RefBancariaModel() {
        refBancariasFile = new File("ProyectoActualizado/IS-PROYECTO-2025-GROUP-05/src/main/data/referencias_bancarias.txt");
        ensureFileExists();
    }

    private void ensureFileExists() {
        if (!refBancariasFile.exists()) {
            try {
                refBancariasFile.getParentFile().mkdirs();
                refBancariasFile.createNewFile();
                System.out.println("Archivo de referencias bancarias creado en: " + refBancariasFile.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error al crear archivo de referencias: " + e.getMessage());
            }
        }
    }

    public boolean agregarReferencia(String referencia, double monto, String banco) {
        if (monto <= 0.009) {
            System.err.println("Error: El monto debe ser mayor a 0.00 Bs");
            return false;
        }

        if (existeReferencia(referencia)) {
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(refBancariasFile, true))) {
            String timestamp = LocalDateTime.now().format(formatter);
            writer.write(referencia + "," + monto + "," + banco + "," + timestamp);
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("Error guardando referencia bancaria: " + e.getMessage());
            return false;
        }
    }

    public RefBancaria buscarReferencia(String referencia) {
        if (!refBancariasFile.exists()) return null;

        try (BufferedReader reader = new BufferedReader(new FileReader(refBancariasFile))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 4 && partes[0].trim().equals(referencia)) {
                    double monto = Double.parseDouble(partes[1].trim());
                    String banco = partes[2].trim();
                    LocalDateTime timestamp = LocalDateTime.parse(partes[3].trim(), formatter);
                    return new RefBancaria(referencia, monto, banco, timestamp);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error buscando referencia: " + e.getMessage());
        }
        return null;
    }

    public boolean existeReferencia(String referencia) {
        return buscarReferencia(referencia) != null;
    }

    public boolean eliminarReferencia(String referencia) {
        if (!refBancariasFile.exists()) return false;

        List<String> lineasMantenidas = new ArrayList<>();
        boolean encontrada = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(refBancariasFile))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 1 && !partes[0].trim().equals(referencia)) {
                    lineasMantenidas.add(linea);
                } else {
                    encontrada = true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error leyendo referencias: " + e.getMessage());
            return false;
        }

        if (!encontrada) return false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(refBancariasFile))) {
            for (String linea : lineasMantenidas) {
                writer.write(linea);
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error eliminando referencia: " + e.getMessage());
            return false;
        }
    }

    public boolean esReferenciaValida(String referencia) {
        if (referencia == null || referencia.trim().isEmpty()) {
            return false;
        }
        
        String ref = referencia.trim();
        return ref.matches("\\d{8,12}") && ref.length() >= 8 && ref.length() <= 12;
    }

    public static class RefBancaria {
        private final String referencia;
        private final double monto;
        private final String banco;
        private final LocalDateTime timestamp;

        public RefBancaria(String referencia, double monto, String banco, LocalDateTime timestamp) {
            this.referencia = referencia;
            this.monto = monto;
            this.banco = banco;
            this.timestamp = timestamp;
        }

        public String getReferencia() { return referencia; }
        public double getMonto() { return monto; }
        public String getBanco() { return banco; }
        public LocalDateTime getTimestamp() { return timestamp; }

        @Override
        public String toString() {
            return String.format("Ref: %s, Monto: %.2f Bs, Banco: %s, Fecha: %s", 
                               referencia, monto, banco, timestamp.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        }
    }
}