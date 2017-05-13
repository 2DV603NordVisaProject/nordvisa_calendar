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
import PendingRegistrationsView from "./PendingRegistrationsView"
import Redirect from "react-router/Redirect";
import Logout from "./Logout";
import MatchWhenLoggedIn from "./MatchWhenLoggedIn";
import MatchWhenAdmin from "./MatchWhenAdmin";

class ViewContainer extends Component {
  render() {
    return (
      <div className="view-container">
        <Match exactly pattern="/" render={() => (
            <Redirect to="/login"/>
        )}/>
        <Match pattern="/login" component={LoginView}/>
        <Match pattern="/logout" component={Logout}/>
        <Match pattern="/register" component={RegisterView}/>
        <Match pattern="/recover-password" component={RecoverView}/>
        <Match pattern="/update-password" component={UpdatePasswordView}/>
        <Match pattern="/generate-widget" component={WidgetView}/>
        <MatchWhenLoggedIn pattern="/user/my-account" component={MyAccountView}/>
        <MatchWhenLoggedIn pattern="/user/create-event" component={CreateView}/>
        <MatchWhenLoggedIn pattern="/user/my-events" component={MyEventsView}/>
        <MatchWhenAdmin pattern="/admin/members" component={MembersView}/>
        <MatchWhenAdmin pattern="/admin/pending-registrations" component={PendingRegistrationsView}/>
      </div>
    );
  }
}

export default ViewContainer;
