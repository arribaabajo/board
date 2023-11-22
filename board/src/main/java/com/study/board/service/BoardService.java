package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;
    //Handles new content submission.
    public void write(Board board){

        boardRepository.save(board);
    }
    //Manages the display of a list of posts.
    public Page<Board> boardlist(Pageable pageable) {

        return boardRepository.findAll(pageable);

    }

    public Board view(Integer id){

        return boardRepository.findById(id).orElse(null);
    }

    // remove bbs content
    public void delete(Integer id){

        boardRepository.deleteById(id);
    }


}
