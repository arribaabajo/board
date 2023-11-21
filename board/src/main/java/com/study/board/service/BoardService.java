package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.reflect.annotation.ExceptionProxy;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;
    //Handles new content submission.
    public void write(Board board, MultipartFile file) throws Exception {

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        UUID uuid = UUID.randomUUID();

        String filename = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, filename);

        file.transferTo(saveFile);

        board.setFilename(filename);
        board.setFilepath("/files"+filename);

        boardRepository.save(board);
    }


    //Manages the display of a list of posts.
    public List<Board> list(){

        return boardRepository.findAll();

    }

    public Board view(Integer id){

        return boardRepository.findById(id).orElse(null);
    }

    // remove bbs content
    public void delete(Integer id){

        boardRepository.deleteById(id);
    }


}
