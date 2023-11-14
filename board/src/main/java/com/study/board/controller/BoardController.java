package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;
    @GetMapping("/board/write") //localhost:8080/board/write
       public String WriteForm(){

        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String WritePro(Board board, Model model){
        boardService.write(board);
        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    //
    @GetMapping("/board/list")
    public String list(Model model){
        model.addAttribute("list", boardService.list());
        return "boardlist";
    }

    @GetMapping("/board/view") //localhost:8080/board/view?id=1
    public String View(Model model, Integer id){

        model.addAttribute("board", boardService.view(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String delete(Integer id){
        boardService.delete(id);

        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String modify(@PathVariable("id")Integer id,
                         Model model){

        model.addAttribute("board", boardService.view(id));
        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String update(@PathVariable("id") Integer id, Board board){
        Board boardTemp = boardService.view(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp);

        return "redirect:/board/list";
    }

}
