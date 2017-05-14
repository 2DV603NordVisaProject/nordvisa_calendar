import React from "react";
import Client from "../Client";
import Match from "react-router/Match";
import Redirect from "react-router/Redirect";

const MatchWhenAdmin = ({component: Component, ...rest}) => (
  <Match {...rest} render={(props) => (
    Client.isLogedIn() ? (
      Client.getUserLevel() > 0 ? (
        <Component {...props}/>
      ) : (
        <Redirect to={{
          pathname: "/user/events",
        }}/>
      )
    ) : (
      <Redirect to={{
        pathname: "/login",
      }}/>
    )
  )}/>
);

export default MatchWhenAdmin;
