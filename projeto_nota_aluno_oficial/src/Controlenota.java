import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Controlenota {
    ResultSet rs;
    PreparedStatement sql;
    Statement comando;
    Connection conexao;
    
    Aluno aluno = new Aluno();

    public void conectar() {
        try {
            // Variáveis com os dados da conexão
            String nomeservidor = "localhost:3306";
            String nomebanco = "notasalunodb";
            String nomeusuario = "root";
            String senha = "";
            
            // Especificação do driver a ser utilizado
            String nomedriver = "com.mysql.cj.jdbc.Driver";
            
            // Ativa o driver
            Class.forName(nomedriver);
            
            // Criando a conexão ao Banco
            String url = "jdbc:mysql://" + nomeservidor + "/" + nomebanco + "?useTimezone=true&serverTimezone=UTC";
            conexao = DriverManager.getConnection(url, nomeusuario, senha);
            comando = conexao.createStatement();
            
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Driver não encontrado !!!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de Acesso ao Banco de Dados: " + e.getMessage());
        }
    }
    public void cadastrar(int numrgm, String nm, double n1, double n2) {
        try {
            aluno.setRgm(numrgm);
            aluno.setNome(nm);
            aluno.setNota1(n1);
            aluno.setNota2(n2);
            
            sql = conexao.prepareCall("Insert into alunos (rgm, nome, nota_1, nota_2) Values (?,?,?,?)");
            
            sql.setInt(1, aluno.getrgm());
            sql.setString(2, aluno.getNome());
            sql.setDouble(3, aluno.getNota1());
            sql.setDouble(4, aluno.getNota2());
            int reg = sql.executeUpdate();  
            if(reg !=0){
                JOptionPane.showMessageDialog(null, "Dados cadastrados");
            }
            else{
                JOptionPane.showMessageDialog(null, "Erro Sql!!!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de conexão com o bda");
          }
    }
}
