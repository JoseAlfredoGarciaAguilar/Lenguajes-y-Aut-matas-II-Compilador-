package ia;

public class Lista<T> {

    private Nodo<T> Frente, Fin;
    private T Dr;

    public Lista() {
        Frente = Fin = null;
        Dr = null;
    }

    public boolean InsertarFin(T Dato) {

        Nodo<T> Nuevo;
        try {
            Nuevo = new Nodo(Dato);
        } catch (Exception e) {
            return false;
        }
        if (Frente == null)// Insertando el primer nodo de la lista
        {
            Frente = Fin = Nuevo;
        } else {// Insertamso el nuevo nodo al final de la lista
            Fin.setSig(Nuevo);
            Fin = Nuevo;
        }
        return true;
    }

    public boolean InsertarFrente(T Dato) {
        Nodo<T> Nuevo;
        try {
            Nuevo = new Nodo(Dato);
        } catch (Exception e) {
            return false;
        }

        if (Frente == null) {
            Frente = Fin = Nuevo;
        } else {
            Nuevo.setSig(Frente);
            Frente = Nuevo;
        }
        return true;
    }

    public boolean InsertarOrdenada(T Dato) {
        if (Frente == null) {
            try {
                Frente = new Nodo(Dato);
            } catch (Exception e) {
                return false;
            }
            Fin = Frente;
            return true;
        }
        // Si inserta al inicio
        String IdDato = Dato.toString();
        String IdFrente = Frente.getInfo().toString();

        if (IdDato.compareTo(IdFrente) <= 0) {// insertar al inicio
            return InsertarFrente(Dato);
        }
        String IdFin = Fin.getInfo().toString();

        if (IdDato.compareTo(IdFin) >= 0) {// insertar al final
            return InsertarFin(Dato);
        }
        Nodo<T> Nuevo;
        try {
            Nuevo = new Nodo(Dato);
        } catch (Exception e) {
            return false;
        }

        // entre dos nodos
        Nodo<T> Aux = Frente;
        Nodo<T> Ant = null;
        while (IdDato.compareTo(Aux.getInfo().toString()) > 0) {
            Ant = Aux;
            Aux = Aux.getSig();
        }
        Ant.setSig(Nuevo);
        Nuevo.setSig(Aux);
        return true;
    }

    public boolean Retirar(int Posicion) {
        if (Frente == null || Posicion > length() || Posicion <= 0) {
            return false;
        }
        Nodo<T> Aux = Frente;
        Nodo<T> Ant = null;
        for (int i = 1; i < Posicion; i++) {
            Ant = Aux;
            Aux = Aux.getSig();
        }
        Dr = Aux.getInfo();
        // Aux ya se posicion� en el nodo que se eliminar�
        //PRIMER CASOM �NICO NODO DE LA LISTA
        if (Frente == Fin) {
            Frente = Fin = null;
            return true;
        }
        // C�mo sabe si aux est� en el primer nodo
        if (Frente == Aux) {
            Frente = Frente.getSig();
            return true;
        }
        // c�mo sabe su aux est� en el �ltimo nodo
        if (Fin == Aux) {
            Fin = Ant;
            Fin.setSig(null);
            return true;
        }
        // Aux est� entre dos nodos
        Ant.setSig(Aux.getSig());
        return true;
    }

    public boolean Retirar(T Dato) {
        if (Frente == null) {
            return false;
        }
        String IdBorrar = Dato.toString();

        int Posicion = 0;
        Nodo<T> Aux = Frente;

        while (Aux != null) {

            Posicion++;
            String IdAux = Aux.getInfo().toString();
            if (IdAux.compareToIgnoreCase(IdBorrar) == 0) {
                break;
            }
            Aux = Aux.getSig();
        }
        //no lo encuentra
        if (Aux == null) {
            return false;
        }
        return Retirar(Posicion);
    }

    public boolean Buscar(T Dato) {
        if (Frente == null) {
            return false;
        }
        String IdBorrar = Dato.toString();
        Nodo<T> Aux = Frente;
        while (Aux != null) {
            String IdAux = Aux.getInfo().toString();
            if (IdAux.compareToIgnoreCase(IdBorrar) == 0) {
                break;
            }
            Aux = Aux.getSig();
        }
        //no lo encuentra
        if (Aux == null) {
            return false;
        }
        Dr = Aux.getInfo();
        return true;
    }

    public int length() {
        Nodo<T> Aux = Frente;
        int Cont = 0;
        while (Aux != null) {
            Cont++;
            Aux = Aux.getSig();
        }
        return Cont;
    }

    public Nodo<T> getFrente() {
        return Frente;
    }

    public Nodo<T> getFin() {
        return Fin;
    }

    public T getDr() {
        return Dr;
    }

}
