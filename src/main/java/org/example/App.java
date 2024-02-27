package org.example;

import org.example.controller.ArticleController;
import org.example.controller.Controller;
import org.example.controller.MemberController;
import org.example.dto.Article;
import org.example.dto.Member;
import org.example.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private List<Article> articles;
    private List<Member> members;

    public App() {
        articles = new ArrayList<>();
        members = new ArrayList<>();
    }

    public void start() {
        System.out.println("== 프로그램 시작 ==");

        makeTestData();

        Scanner sc = new Scanner(System.in);

        MemberController memberController = new MemberController(sc, members);
        ArticleController articleController = new ArticleController(sc, articles);

        while ( true ) {
            System.out.printf("명령어) ");
            String cmd = sc.nextLine();

            cmd = cmd.trim();

            if ( cmd.length() == 0 ) {
                continue;
            }

            if ( cmd.equals("system exit") ) {
                break;
            }

            String[] cmdBits = cmd.split(" "); // article detail 1

            if ( cmdBits.length == 1 ) {
                System.out.println("존재하지 않는 명령어 입니다.");
                continue;
            }
            String controllerName = cmdBits[0]; // article
            String actionMethodName = cmdBits[1]; // detail/modify

            Controller controller = null;

            if ( controllerName.equals("article") ) {
                controller = articleController;
            }
            else if ( controllerName.equals("member") ) {
                controller = memberController;
            }
            else {
                System.out.println("존재하지 않는 명령어 입니다.");
                continue;
            }

            controller.doAction(cmd, actionMethodName);
        }

        sc.close();

        System.out.println("== 프로그램 끝 ==");
    }

    private void makeTestData() {
        System.out.println("테스트를 위한 데이터를 생성합니다.");

        articles.add(new Article(1, Util.getNowDateStr(), "제목1", "내용1", 10));
        articles.add(new Article(2, Util.getNowDateStr(), "제목2", "내용2", 32));
        articles.add(new Article(3, Util.getNowDateStr(), "제목3", "내용3", 108));
    }
}