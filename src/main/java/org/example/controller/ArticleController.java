package org.example.controller;

import org.example.container.Container;
import org.example.dto.Article;
import org.example.dto.Member;
import org.example.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArticleController extends Controller {
    private Scanner sc;
    private List<Article> articles;
    private String cmd;
    private String actionMethodName;

    public ArticleController(Scanner sc) {
        this.sc = sc;

        articles = Container.articleDao.articles;
    }

    public void makeTestData() {
        System.out.println("테스트를 위한 게시물 데이터를 생성합니다.");

        articles.add(new Article(1, Util.getNowDateStr(), 1, "제목1", "내용1", 10));
        articles.add(new Article(2, Util.getNowDateStr(), 2, "제목2", "내용2", 32));
        articles.add(new Article(3, Util.getNowDateStr(), 2, "제목3", "내용3", 108));
    }

    public void doAction(String cmd, String actionMethodName) {
        this.cmd = cmd;
        this.actionMethodName = actionMethodName;

        switch ( actionMethodName ) {
            case "write":
                doWrite();
                break;
            case "list":
                showList();
                break;
            case "detail":
                showDetail();
                break;
            case "modify":
                doModify();
                break;
            case "delete":
                doDelete();
                break;
            default:
                System.out.println("존재하지 않는 명령어입니다.");
                break;
        }
    }

    public void doWrite() {
        int id = articles.size() + 1;
        String regDate = Util.getNowDateStr();
        System.out.printf("제목 : ");
        String title = sc.nextLine();
        System.out.printf("내용 : ");
        String body = sc.nextLine();

        Article article = new Article(id, regDate, 1, title, body);

        articles.add(article);

        System.out.printf("%d번 글이 작성되었습니다.\n", id);
    }

    public void showList() {
        if ( articles.size() == 0 ) {
            System.out.println("게시물이 없습니다.");
            return;
        }

        String searchKeyword = cmd.substring("article list".length()).trim();

        List<Article> forListArticles = articles;

        if ( searchKeyword.length() > 0 ) {
            forListArticles = new ArrayList<>();

            for (Article article : articles ) {
                if ( article.title.contains(searchKeyword) ) {
                    forListArticles.add(article);
                }
            }
        }

        if ( forListArticles.size() == 0 ) {
            System.out.println("검색결과가 존재하지 않습니다.");
            return;
        }


        System.out.println("번호 |    작성자 | 조회 | 제목");

        for ( int i = forListArticles.size() - 1; i >= 0; i-- ) {
            Article article = forListArticles.get(i);

            String writerName = null;

            List<Member> members = Container.memberDao.members;

            for ( Member member : members ) {
                if ( article.memberId == member.id ) {
                    writerName = member.name;
                    break;
                }
            }

            System.out.printf("%4d | %6s | %4d | %s\n", article.id, writerName, article.hit, article.title);
        }
    }

    public void showDetail() {
        String[] cmdBits = cmd.split(" ");

        if ( cmdBits.length <= 2 ) {
            System.out.println("게시물 번호를 입력해주세요.");
            return;
        }

        int id = Integer.parseInt(cmdBits[2]);



        Article foundArticle = getArticleById(id);

        if ( foundArticle == null ) {
            System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
            return;
        }

        foundArticle.increaseHit();

        System.out.printf("번호 : %d\n", foundArticle.id);
        System.out.printf("날짜 : %s\n", foundArticle.regDate);
        System.out.printf("제목 : %s\n", foundArticle.title);
        System.out.printf("내용 : %s\n", foundArticle.body);
        System.out.printf("조회 : %d\n", foundArticle.hit);
    }

    public void doModify() {
        String[] cmdBits = cmd.split(" ");

        if ( cmdBits.length <= 2 ) {
            System.out.println("게시물 번호를 입력해주세요.");
            return;
        }

        int id = Integer.parseInt(cmdBits[2]);

        Article foundArticle = getArticleById(id);

        if ( foundArticle == null ) {
            System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
            return;
        }

        System.out.printf("제목 : ");
        String title = sc.nextLine();
        System.out.printf("내용 : ");
        String body = sc.nextLine();

        foundArticle.title = title;
        foundArticle.body = body;

        System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
    }

    public void doDelete() {
        String[] cmdBits = cmd.split(" ");

        if ( cmdBits.length <= 2 ) {
            System.out.println("게시물 번호를 입력해주세요.");
            return;
        }

        int id = Integer.parseInt(cmdBits[2]);

        int foundIndex = getArticleIndexById(id);

        if ( foundIndex == -1 ) {
            System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
            return;
        }

        articles.remove(foundIndex);

        System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
    }

    private int getArticleIndexById(int id) {
        int i = 0;
        for ( Article article : articles ) {
            if (article.id == id ) {
                return i;
            }
            i++;
        }

        return -1;
    }

    private Article getArticleById(int id) {
        int index = getArticleIndexById(id);

        if ( index != -1 ) {
            return articles.get(index);
        }

        return null;
    }
}