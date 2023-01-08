/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia;

/**
 *
 * @author ADM
 */
public class Nodo<T> {

    public T Info;
    private Nodo<T> Sig;

    public Nodo(T d) {
        Info = d;
        Sig = null;
    }

    public Nodo<T> DameSig() {
        return Sig; // regresa enlace o referencia, apuntador al siguiente nodo
    }

    public void setSig(Nodo<T> Ap) {
        Sig = Ap;
    }
}