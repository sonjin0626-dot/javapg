
// Webサーバーを使うための部品を読み込みます。
import com.sun.net.httpserver.HttpServer;
// 待ち受ける番号を指定するための部品を読み込みます。【動かない】
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList; // Todoを入れるリストを作るために読み込みます。
import java.util.List; // Todoを文字列の一覧として扱うために読み込みます。

// 実行するプログラムの名前を App にします。【動かない】
public class App {
    // プログラムを起動したときに最初に動く部分です。【1】
    public static void main(String[] args) throws Exception {
        // 8080 番で待ち受けるサーバーを用意します。【1】
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        // 「/」へのアクセスが来たときの処理をここに書きます。【1】
        server.createContext("/", exchange -> {
            // 送り返す文字を message に入れます。【毎】
            // アクセスされたパスを取り出します。
            String path = exchange.getRequestURI().getPath();
            String message;
            // 文字を UTF-8 で返すことをブラウザに伝ます。【毎】
            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
            // パスを比べて、返す文字を決めます。
            if (path.equals("/hello")) {
                String query = exchange.getRequestURI().getRawQuery(); // URLの「?」より後ろを取り出します。
                if (query == null) {
                    query = "name=ゲスト";
                }
                String name = query.substring(5); // 「name=」の後ろを切り出します。
                name = URLDecoder.decode(name, StandardCharsets.UTF_8); // %で始まる表記を日本語に戻します。
                message = "こんにちは、" + name + "さん！"; // 名前をあいさつに混ぜます。
            } else if (path.equals("/todos")) { // Todo一覧のパスを処理します。
                List<String> todos = new ArrayList<>(); // Todoの文字列を入れるリストを作ります。
                todos.add("牛乳を買う"); // 1件目を入れます。
                todos.add("卵を買う"); // 2件目を入れます。
                todos.add("パンを買う"); // 3件目を入れます。
                todos.add("自分の今日のTodo");// 4件目を入れます。
                String html = "<ul>"; // 一覧のHTMLを始めます。
                for (String todo : todos) { // Todoを1件ずつ取り出します。
                    html += "<li>" + todo + "</li>"; // 1件を一覧に足します。
                }
                html += "</ul>"; // 一覧のHTMLを閉じます。
                message = html; // 組み立てたHTMLを返す内容にします。
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8"); // HTMLとして返します。
            } else if (path.equals("/bye")) {
                message = "さようなら！";
            } else if (path.equals("/main")) {
                message = "今日の定食はカレー";
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
