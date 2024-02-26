package org.example;

import org.example.dto.Article;
import org.example.dto.Member;
import org.example.util.Util;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Exam {
    private static List<Article> articles;
    private static List<Member> members;

    static {
        articles = new ArrayList<>();
        members = new ArrayList<>();
    }

    public static void main(String[] args) {
        System.out.println("== 프로그램 시작 ==");

        makeTestData();

        Scanner sc = new Scanner(System.in);

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
            else if ( cmd.equals("member join") ) {
                int id = members.size() + 1;
                String regDate = Util.getNowDateStr();

                String loginId = null;
                while( true ) {
                    System.out.printf("로그인 아이디 : ");
                    loginId = sc.nextLine();

                    if ( isJoinableLoginId(loginId) == false ) {
                        System.out.printf("%s(은)는 이미 사용중인 아이디 입니다.\n", loginId);
                        continue;
                    }
                    break;
                }

                String loginPw = null;
                String loginIdConfirm = null;

                while ( true ) {
                    System.out.printf("로그인 비번 : ");
                    loginPw = sc.nextLine();
                    System.out.printf("로그인 비번확인 : ");
                    loginIdConfirm = sc.nextLine();

                    if ( loginPw.equals(loginIdConfirm) == false ) {
                        System.out.println("비밀번호를 다시 입력해주세요.");
                        continue;
                    }
                    break;
                }

                System.out.printf("이름 : ");
                String name = sc.nextLine();
                Member member = new Member(id, regDate, loginId, loginPw, name);

                members.add(member);

                System.out.printf("%d번 회원이 생성되었습니다!\n", id);
            }
            else if ( cmd.equals("article write") ) {
                int id = articles.size() + 1;
                String regDate = Util.getNowDateStr();
                System.out.printf("제목 : ");
                String title = sc.nextLine();
                System.out.printf("내용 : ");
                String body = sc.nextLine();
                Article article = new Article(id, regDate, title, body);

                articles.add(article);

                System.out.printf("%d번 글이 작성되었습니다.\n", id);
            }
            else if ( cmd.startsWith("article list") ) {
                if ( articles.size() == 0 ) {
                    System.out.println("게시물이 없습니다.");
                    continue;
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
                    continue;
                }


                System.out.println("번호 | 조회 | 제목");

                for ( int i = forListArticles.size() - 1; i >= 0; i-- ) {
                    Article article = forListArticles.get(i);

                    System.out.printf("%4d | %4d | %s\n", article.id, article.hit, article.title);
                }
            }
            else if ( cmd.startsWith("article detail ") ) {
                String[] cmdBits = cmd.split(" ");
                int id = Integer.parseInt(cmdBits[2]);

                Article foundArticle = getArticleById(id);

                if ( foundArticle == null ) {
                    System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
                    continue;
                }

                foundArticle.increaseHit();

                System.out.printf("번호 : %d\n", foundArticle.id);
                System.out.printf("날짜 : %s\n", foundArticle.regDate);
                System.out.printf("제목 : %s\n", foundArticle.title);
                System.out.printf("내용 : %s\n", foundArticle.body);
                System.out.printf("조회 : %d\n", foundArticle.hit);
            }
            else if ( cmd.startsWith("article modify ") ) {
                String[] cmdBits = cmd.split(" ");
                int id = Integer.parseInt(cmdBits[2]);

                Article foundArticle = getArticleById(id);

                if ( foundArticle == null ) {
                    System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
                    continue;
                }

                System.out.printf("제목 : ");
                String title = sc.nextLine();
                System.out.printf("내용 : ");
                String body = sc.nextLine();

                foundArticle.title = title;
                foundArticle.body = body;

                System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
            }
            else if ( cmd.startsWith("article delete ") ) {
                String[] cmdBits = cmd.split(" ");
                int id = Integer.parseInt(cmdBits[2]);

                int foundIndex = getArticleIndexById(id);

                if ( foundIndex == -1 ) {
                    System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
                    continue;
                }

                articles.remove(foundIndex);

                System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
            }
            else {
                System.out.printf("%s(은)는 존재하지 않는 명령어 입니다.\n", cmd);
            }
        }

        sc.close();

        System.out.println("== 프로그램 끝 ==");
    }

    private static boolean isJoinableLoginId(String loginId) {
        int index = getMemberIndexByLoginId(loginId);

        if ( index == -1 ) {
            return true;
        }

        return false;
    }

    private static int getMemberIndexByLoginId(String loginId) {
        int i = 0;

        for ( Member member : members ) {
            if ( member.loginId.equals(loginId) ) {
                return i;
            }
            i++;
        }

        return -1;
    }

    private static int getArticleIndexById(int id) {
        int i = 0;
        for ( Article article : articles ) {
            if (article.id == id ) {
                return i;
            }
            i++;
        }

        return -1;
    }

    private static Article getArticleById(int id) {
        int index = getArticleIndexById(id);

        if ( index != -1 ) {
            return articles.get(index);
        }

        return null;
    }

    private static void makeTestData() {
        System.out.println("테스트를 위한 데이터를 생성합니다.");

        articles.add(new Article(1, Util.getNowDateStr(), "제목1", "내용1", 10));
        articles.add(new Article(2, Util.getNowDateStr(), "제목2", "내용2", 32));
        articles.add(new Article(3, Util.getNowDateStr(), "제목3", "내용3", 108));
    }
}