import React, { Component } from "react";
import "./MembersView.css";
import Client from "../Client";
import MembersList from "./MembersList";
import ConfirmMessage from "./ConfirmMessage";
import PropTypes from "prop-types";

class MembersView extends Component {
  state = {
    members: [],
    updated: [],
    popup: {
      pop: false,
      msg: "",
      onYesClick: this.onYesClick,
    },
  }
  componentWillMount() {
    const members = Client.getMembers();
    this.setState({ members });
  }

  onFormSubmit(event) {
    event.preventDefault();
    const updated = this.state.updated.join(", ");
    const popup = {
      pop: true,
      msg: `${this.context.language.MembersView.accMsg} ${updated}`,
    }

    this.setState({ popup });
  }

  onInputChange(event) {
    const updated = this.state.updated;
    const members = this.state.members.filter((member) => {
      if (event.target.name === member.email) {
        updated.push(event.target.name);
        member.userLevel = event.target.value;
        return member;
      } else {
        return member;
      }
    });
    this.setState({ members });
  }

  onConfirmClick() {
    //TODO;
  }

  render() {

    const language = this.context.language.MembersView;

    return (
      <div className="members view">
        <h2 className="uppercase">{language.members}</h2>
        <form onSubmit={this.onFormSubmit.bind(this)}>
          <MembersList members={this.state.members} onChange={this.onInputChange.bind(this)}/>
          <input type="submit" value={language.save} className="btn-primary"></input>
        </form>
        <ConfirmMessage popup={this.state.popup} onClick={this.onConfirmClick.bind(this)}/>
      </div>
    );
  }
}

MembersView.contextTypes = {
  language: PropTypes.object,
}

export default MembersView;
