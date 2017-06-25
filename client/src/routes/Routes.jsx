import React from 'react';
import Miss from 'react-router/Miss';
import Match from 'react-router/Match';
import Redirect from 'react-router/Redirect';
import './Routes.css';
import LoginView from '../pages/LoginPage';
import RegisterView from '../pages/RegisterPage';
import RecoverView from '../pages/RecoverPasswordPage';
import UpdatePasswordView from '../pages/ChangePasswordPage';
import MyAccountView from '../pages/MyAccountPage';
import MembersView from '../pages/MembersPage';
import EventContainer from '../pages/EventPage';
import MyEventsView from '../pages/MyEventsPage';
import WidgetView from '../pages/GenerateWidgetPage';
import PendingRegistrationsView from '../pages/PendingRegistrationsPage';
import Logout from '../components/Logout';
import MatchWhenLoggedIn from './MatchWhenLoggedIn';
import MatchWhenAdmin from './MatchWhenAdmin';
import Client from '../Client';


const ViewContainer = () => (
  <div className="view-container">
    <Miss render={({ location }) => (
      <div className="lightbox" style={{ margin: '80px auto' }}>
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
    <MatchWhenLoggedIn pattern="/user/event/create" component={EventContainer} />
    <MatchWhenLoggedIn exactly pattern="/user/event" component={MyEventsView} />
    <Match
      pattern="/user/event/edit/:id"
      render={({ params }) => {
        if (Client.isLogedIn()) {
          return (
            <EventContainer id={params.id} progress="edit" />
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
            <EventContainer id={params.id} progress="view" />
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
