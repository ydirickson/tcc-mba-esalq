package br.usp.exemplo.graduacao.entidades;

@Data
public class Aluno {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;

}
