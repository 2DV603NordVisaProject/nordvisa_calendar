import React from 'react';
import Miss from 'react-router/Miss';
import Match from 'react-router/Match';
import Redirect from 'react-router/Redirect';
import './ViewContainer.css';
import LoginView from './LoginView';
import RegisterView from './RegisterView';
import RecoverView from './RecoverView';
import UpdatePasswordView from './UpdatePasswordView';
import MyAccountView from './MyAccountView';
import MembersView from './MembersView';
import CreateView from './CreateView';
import MyEventsView from './MyEventsView';
import WidgetView from './WidgetView';
import PendingRegistrationsView from './PendingRegistrationsView';
import Logout from './Logout';
import MatchWhenLoggedIn from './MatchWhenLoggedIn';
import MatchWhenAdmin from './MatchWhenAdmin';
import Client from '../Client';


const ViewContainer = () => (
  <div className="view-container">
    <Miss render={({ location }) => (
      <div className="lightbox error404">
        <h2>404</h2>
        <h3>{location.pathname} could not be found!</h3>
      </div>
      )}
    />
    <Match
      exactly
      pattern="/"
      render={() => (
        <Redirect to="/login" />
    )}
    />
    <Match pattern="/login" component={LoginView} />
    <Match pattern="/logout" component={Logout} />
    <Match pattern="/register" component={RegisterView} />
    <Match pattern="/recover-password" component={RecoverView} />
    <Match
      pattern="/update-password/:id"
      render={({ params }) => (
        <UpdatePasswordView id={params.id} />
        )}
    />
    <Match pattern="/generate-widget" component={WidgetView} />
    <MatchWhenLoggedIn pattern="/user/account" component={MyAccountView} />
    <MatchWhenLoggedIn pattern="/user/event/create" component={CreateView} />
    <MatchWhenLoggedIn exactly pattern="/user/event" component={MyEventsView} />
    <Match
      pattern="/user/event/edit/:id"
      render={({ params }) => {
        if (Client.isLogedIn()) {
          return (
            <CreateView id={params.id} progress="edit" />
          );
        }
        return (
          <Redirect to="/login" />
        );
      }}
    />

    <Match
      pattern="/user/event/view/:id"
      render={({ params }) => {
        if (Client.isLogedIn()) {
          return (
            <CreateView id={params.id} progress="view" />
          );
        }
        return (
          <Redirect to="/login" />
        );
      }}
    />
    <MatchWhenAdmin pattern="/admin/members" component={MembersView} />
    <MatchWhenAdmin pattern="/admin/pending-registrations" component={PendingRegistrationsView} />
  </div>
);

export default ViewContainer;
