import java.util.ArrayList; // ArrayList（データを順番に入れる箱）を使えるようにします
import java.util.List; // List（複数のデータをまとめる型）を使えるようにします

public class Main { // Mainという名前のクラス（プログラムのまとまり）を定義します
    public static void main(String[] args) { // プログラムを開始する特別なメソッドを定義します
        List<Todo> todos = new ArrayList<>(); // Todoを入れるList（複数のTodoを入れる箱）を作ります
        todos.add(new Todo("牛乳を買う", false)); // 未完了のTodoを1件追加します
        todos.add(new Todo("ゴミを出す", true)); // 完了したTodoを1件追加します
        todos.add(new Todo("散歩に行く", false)); // 未完了のTodoを1件追加します
        todos.add(new Todo("掃除をする", false)); // 未完了のTodoを1件追加します
        todos.add(new Todo("パンを買う", true)); // 完了したTodoを1件追加します

        for (Todo todo : todos) { // ListのTodoを1件ずつ繰り返し取り出します
            System.out.println(todo.toItem()); // TodoをHTMLのli形式に変えて1行表示します
        } // forの繰り返しを終わります
    } // mainメソッドを終わります
} // Mainクラスを終わります

class Todo { // Todoというクラス（データと処理をまとめたもの）を定義します
    private String title; // title（Todoのタイトル）を保存する変数です
    private boolean done; // done（Todoが完了したかどうか）を保存する変数です

    public Todo(String title, boolean done) { // Todoを作るときの処理（コンストラクタ）を定義します
        this.title = title; // 受け取ったタイトルをtitleに保存します
        this.done = done; // 受け取った完了状態をdoneに保存します
    } // コンストラクタを終わります

    public String toItem() { // Todoをli形式の文字列に変えるメソッド（処理）を定義します
        String mark = done ? "[とんだ化け物だ] " : ""; // doneがtrueなら済マークを付け、falseなら何も付けません
        return "<li>" + mark + title + "</li>"; // liタグで囲んだ1行の文字列を返します
    } // toItemメソッドを終わります
} // Todoクラスを終わります