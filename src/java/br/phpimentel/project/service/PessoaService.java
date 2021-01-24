package br.phpimentel.project.service;

import br.phpimentel.project.model.PessoaModel;
import br.phpimentel.project.service.dao.PessoaDAO;
import java.util.List;

/**
 *
 * @author phpimentel
 */
public class PessoaService {
    
    private PessoaDAO pessoaDAO = new PessoaDAO();
    
    public List<PessoaModel> listPessoa() {
        return this.pessoaDAO.getListPessoa();
    }
    
    public List<PessoaModel> listarPessoaFilter(int first, int pageSize, String sortField, boolean asc) {
        return this.pessoaDAO.listarPessoaFilter(first, pageSize, sortField, asc);
    }
    
    public int getRowCount() {
        return this.pessoaDAO.getRowCount();
    }
}
