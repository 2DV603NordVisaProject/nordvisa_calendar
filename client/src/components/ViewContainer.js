import React, { Component } from "react";
import "./ViewContainer.css";
import LoginView from "./LoginView";
import Match from "react-router/Match";
import RegisterView from "./RegisterView";
import RecoverView from "./RecoverView";
import UpdatePasswordView from "./UpdatePasswordView";
import MyAccountView from "./MyAccountView";
import MembersView from "./MembersView";
import CreateView from "./CreateView";
import MyEventsView from "./MyEventsView";
import WidgetView from "./WidgetView";

class ViewContainer extends Component {
  render() {
    return (
      <div className="view-container">
        <Match pattern="/login" component={LoginView}/>
        <Match pattern="/register" component={RegisterView}/>
        <Match pattern="/recover-password" component={RecoverView}/>
        <Match pattern="/update-password" component={UpdatePasswordView}/>
        <Match pattern="/generate-widget" component={WidgetView}/>
        <Match pattern="/user/my-account" component={MyAccountView}/>
        <Match pattern="/user/create-event" component={CreateView}/>
        <Match pattern="/admin/members" component={MembersView}/>
        <Match pattern="/user/my-events" component={MyEventsView}/>
      </div>
    );
  }
}

export default ViewContainer;
