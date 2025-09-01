import { useEffect, useState } from "react";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

export default function ChatPage() {
  const [stompClient, setStompClient] = useState(null);
  const [messages, setMessages] = useState([]);
  const [messageText, setMessageText] = useState("");
const username = localStorage.getItem("userName");

  useEffect(() => {
    const socket = new SockJS("http://localhost:9099/message-service/ws");
    const client = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      onConnect: () => {
        console.log("Connected!");
        client.subscribe("/topic/messages", (msg) => {
          setMessages(prev => [...prev, JSON.parse(msg.body)]);
        });
      },
      onStompError: (err) => {
        console.error("STOMP Error:", err);
      }
    });
    client.activate();
    setStompClient(client);

    return () => client.deactivate();
  }, []);

  const sendMessage = () => {
    if (stompClient && stompClient.connected) {
      stompClient.publish({
        destination: "/app/sendMessage",
        body: JSON.stringify({ sender: username, content: messageText }),
      });
      setMessageText("");
    } else {
      console.log("Cannot send message: not connected yet!");
    }
  };

  return (
    <div>
      <div>
        {messages.map((msg, i) => <p key={i}>{msg.sender}: {msg.content}</p>)}
      </div>
      <input value={messageText} onChange={e => setMessageText(e.target.value)} />
      <button onClick={sendMessage}>Send</button>
    </div>
  );
}
