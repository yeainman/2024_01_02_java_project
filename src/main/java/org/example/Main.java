package org.example;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("== 프로그램 시작 ==");
        Scanner sc = new Scanner(System.in);

        int lastArticleId = 0;

        List<Article> articles = new ArrayList<>();

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
            else if ( cmd.equals("article list") ) {
                if ( articles.size() == 0 ) {
                    System.out.println("게시물이 없습니다.");
                    continue;
                }

                System.out.println("번호 | 제목");

                for ( int i = articles.size() - 1; i >= 0; i-- ) {
                    Article article = articles.get(i);

                    System.out.printf("%d | %s\n", article.id, article.title);
                }
            }
            else if ( cmd.equals("article write") ) {
                int id = lastArticleId + 1;
                System.out.printf("제목 : ");
                String title = sc.nextLine();
                System.out.printf("내용 : ");
                String body = sc.nextLine();

                lastArticleId = id;
                // Article article = new Article(id, title, body); Exam에서 사용하므로 일단 오류 제거를 위한 주석 처리

                // articles.add(article);

                System.out.printf("%d번 글이 작성되었습니다.\n", id);
            }
            else {
                System.out.printf("%s(은)는 존재하지 않는 명령어 입니다.\n", cmd);
            }
        }

        sc.close();

        System.out.println("== 프로그램 끝 ==");
    }
}

class Article {
    int id;
    String regDate;
    String title;
    String body;

    public Article (int id, String regDate, String title, String body) {
        this.id = id;
        this.regDate = regDate;
        this.title = title;
        this.body = body;
    }
}