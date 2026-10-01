
// Webサーバーを使うための部品を読み込みます。
import com.sun.net.httpserver.HttpServer;
// 待ち受ける番号を指定するための部品を読み込みます。【動かない】
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList; // Todoを入れるリストを作るために読み込みます。
import java.util.List; // ★変更 Todoの一覧として扱うために読み込みます。

class Todo { // ★変更
    private final int id; // ★変更
    private final String title; // ★変更
    private boolean done; // ★変更

    Todo(int id, String title) { // ★変更
        this.id = id; // ★変更
        this.title = title; // ★変更
        this.done = false; // ★変更
    }

    int getId() {
        return id;
    } // ★変更

    String getTitle() {
        return title;
    } // ★変更

    boolean isDone() {
        return done;
    } // ★変更

    void setDone(boolean done) {
        this.done = done;
    } // ★変更
}

// 実行するプログラムの名前を App にします。【動かない】
public class App {
    static List<Todo> todos = new ArrayList<>(); // ★変更
    static int nextId = 1; // ★変更

    // プログラムを起動したときに最初に動く部分です。【1】
    public static void main(String[] args) throws Exception {
        // 8080 番で待ち受けるサーバーを用意します。【1】
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        // 「/」へのアクセスが来たときの処理をここに書きます。【1】
        todos.add(new Todo(nextId++, "牛乳を買う")); // ★変更
        Todo egg = new Todo(nextId++, "卵を買う"); // ★変更
        egg.setDone(true);// ★変更
        todos.add(egg); // ★変更
        server.createContext("/", exchange -> {
            // 送り返す文字を message に入れます。【毎】
            // アクセスされたパスを取り出します。
            String path = exchange.getRequestURI().getPath();
            String method = exchange.getRequestMethod(); // 送信方法を取得します。
            String message;
            // 文字を UTF-8 で返すことをブラウザに伝ます。【毎】
            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
            // パスを比べて、返す文字を決めます。
            if (path.equals("/add") && method.equals("POST")) { // 送られたTodoを追加します。
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8); // 送信された文字を読みます。
                String value = body.substring(5); // 「todo=」を除きます。
                String title = URLDecoder.decode(value, StandardCharsets.UTF_8); // ★変更 日本語を元に戻します。
                if (!title.isEmpty()) { // ★変更 空の入力は追加しません。
                    todos.add(new Todo(nextId, title)); // ★変更
                    nextId++; // ★変更
                }
                exchange.getResponseHeaders().set("Location", "/"); // 戻り先を指定します。
                exchange.sendResponseHeaders(303, -1); // 入力後に一覧へ移動する応答を返します。
                exchange.close(); // 応答を閉じます。
                return; // この分岐の処理を終えます。
            } else if (path.equals("/done") && method.equals("GET")) { // ★追加
                String query = exchange.getRequestURI().getQuery(); // ★追加
                if (query != null && query.startsWith("id=") && query.length() > 3) { // ★追加
                    try { // ★追加
                        int id = Integer.parseInt(query.substring(3)); // ★追加
                        for (Todo todo : todos) { // ★追加
                            if (todo.getId() == id) { // ★追加
                                todo.setDone(true); // ★追加
                                break; // ★追加
                            } // ★追加
                        } // ★追加
                    } catch (NumberFormatException e) { // ★追加
                    } // ★追加
                } // ★追加
                exchange.getResponseHeaders().set("Location", "/"); // ★追加
                exchange.sendResponseHeaders(303, -1); // ★追加
                exchange.close(); // ★追加
                return; // ★追加
            } else if (path.equals("/delete") && method.equals("GET")) { // ★追加
                String query = exchange.getRequestURI().getQuery(); // ★追加
                if (query != null && query.startsWith("id=") && query.length() > 3) { // ★追加
                    try { // ★追加
                        int id = Integer.parseInt(query.substring(3)); // ★追加
                        todos.removeIf(todo -> todo.getId() == id); // ★追加
                    } catch (NumberFormatException e) { // ★追加
                    } // ★追加
                } // ★追加
                exchange.getResponseHeaders().set("Location", "/"); // ★追加
                exchange.sendResponseHeaders(303, -1); // ★追加
                exchange.close(); // ★追加
                return; // ★追加
            } else if (path.equals("/")) { // 入力フォームとTodo一覧を表示します。
                String html = "<form method='post' action='/add'><input name='todo'><button>追加</button></form>"; // 入力フォームを作ります。
                html += "<ul>"; // 一覧のHTMLを始めます。
                for (Todo todo : todos) { // ★変更 Todoを1件ずつ取り出します。
                    String mark = ""; // ★変更
                    if (todo.isDone()) { // ★変更
                        mark = " ✔"; // ★変更
                    }
                    html += "<li>" + todo.getTitle() + mark + " <a href='/done?id=" + todo.getId()
                            + "'>完了</a> <a href='/delete?id=" + todo.getId() + "'>削除</a></li>"; // ★追加
                }
                html += "</ul>"; // 一覧のHTMLを閉じます。
                message = html; // 組み立てたHTMLを返す内容にします。
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8"); // HTMLとして返します。
            } else {
                message = "ページが見つかりません";
            }
            // 文字を UTF-8 のバイト列に変えます。【毎】
            byte[] body = message.getBytes("UTF-8");
            // 成功を表す 200 と、送るデータの長さを伝えます。【毎】
            exchange.sendResponseHeaders(200, body.length);
            // データをブラウザへ送ります。【毎】
            exchange.getResponseBody().write(body);
            // 送り終わったことを伝えます。【毎】
            exchange.getResponseBody().close();
            // アクセスが来たときの処理を終えます。【毎】
        });
        // サーバーを起動します。【1】
        server.start();
        // 起動したことと止め方を表示します。【動かない】
        System.out.println("サーバー起動: http://localhost:8080 （止めるときは Ctrl+C）");
        // 最初に動く部分を終えます。【1】
    }
    // App の終わりです。【動かない】
}
