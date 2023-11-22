package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

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
    public String List(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
       Page<Board> list = boardService.boardlist(pageable);

       int nowPage = list.getPageable().getPageNumber() + 1;
       int startPage = Math.max(nowPage - 4, 1);
       int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

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
