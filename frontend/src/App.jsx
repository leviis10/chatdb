import ChatRoom from "./components/ChatRoom/ChatRoom";
import Login from "./components/Login";
import User from "./components/User";
import stompClient from "./utils/stompClient";
import Chat from "./components/Chat";

function App() {
  stompClient.connect();

  return (
    <>
      <h1>Chat DB</h1>

      <Login />
      <hr />

      <User />
      <hr />

      <ChatRoom />
      <br />

      <Chat />
    </>
  );
}

export default App;
