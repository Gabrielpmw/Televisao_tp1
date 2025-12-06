package br.unitins.tp1.service.Imagem;

import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.File;
import java.io.IOException;

public interface FileService {
    void salvar(Long id, FileUpload file) throws IOException;
    File download(String nomeArquivo);
}
