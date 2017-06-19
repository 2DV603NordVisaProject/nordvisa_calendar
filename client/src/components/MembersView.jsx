import React, { Component } from 'react';
import PropTypes from 'prop-types';
import './MembersView.css';
import Client from '../Client';
import MembersList from './MembersList';
import ConfirmMessage from './ConfirmMessage';
import ErrorList from './ErrorList';
import PageTitle from './PageTitle';
import Button from './Button';

class MembersView extends Component {
  constructor() {
    super();

    this.onFormSubmit = this.onFormSubmit.bind(this);
    this.onInputChange = this.onInputChange.bind(this);
  }
  state = {
    members: [],
    updated: [],
    fieldErrors: [],
    popup: {
      pop: false,
      msg: '',
      onYesClick: this.onYesClick,
    },
  }
  componentWillMount() {
    const uri = '/api/admin/manageableUsers';

    Client.get(uri)
      .then((members) => {
        if (Array.isArray(members)) {
          this.setState({ members });
        }
      });
  }


  onFormSubmit(event) {
    event.preventDefault();

    if (this.state.updated.length > 0) {
      let updated = this.state.updated.map(user => user.email);
      updated = updated.join(', ');
      const popup = {
        pop: true,
        msg: `${this.context.language.MembersView.accMsg} ${updated}`,
      };

      this.setState({ popup });
    }
  }

  onInputChange(event) {
    const updated = this.state.updated;
    const members = this.state.members.filter((member) => {
      if (event.target.name === member.id) {
        updated.push({
          id: event.target.name,
          role: event.target.value,
          email: member.email,
        });

        const user = Object.assign({}, member);
        user.userLevel = event.target.value;
        return user;
      }
      return member;
    });

    this.setState({ members, updated });
  }

  onConfirmClick() {
    let updated = this.state.updated;

    while (updated.length > 0) {
      const user = updated.shift();
      let uri = '';

      if (user.role === 'USER') {
        uri = '/api/admin/make_user';
      }

      if (user.role === 'ADMIN') {
        uri = '/api/admin/make_admin';
      }

      if (user.role === 'SUPER_ADMIN') {
        uri = '/api/super_admin/make_super_admin';
      }

      if (user.role === 'DELETE') {
        uri = '/api/user/unregister';
      }

      Client.post({ id: user.id }, uri)
        .then(() => {
          uri = '/api/admin/manageableUsers';

          Client.get(uri)
            .then((members) => {
              this.setState({ members });
            });
        });
    }

    updated = [];
    const popup = {
      pop: false,
      msg: '',
    };
    const fieldErrors = [];
    fieldErrors.push(this.context.language.Errors.accessUpdated);
    this.setState({ updated, popup, fieldErrors });
    this.forceUpdate();
  }

  render() {
    console.log(this);
    const language = this.context.language.MembersView;

    return (
      <div className="members view">
        <PageTitle>{language.members}</PageTitle>
        <form onSubmit={this.onFormSubmit}>
          <MembersList members={this.state.members} onChange={this.onInputChange} />
          <ErrorList errors={this.state.fieldErrors} />
          <Button form>{language.save}</Button>
        </form>
        <ConfirmMessage popup={this.state.popup} onClick={this.onConfirmClick} />
      </div>
    );
  }
}

MembersView.contextTypes = {
  language: PropTypes.object,
};

export default MembersView;
