import React from 'react';
import { shallow } from 'enzyme';
import Event from '../src/components/Event';
import en from '../src/i18n/en';

describe('Event', () => {
  let wrapper;
  const event = {
    id: 1,
    name: 'Sample event',
  };
  const context = { language: en };

  const onDelete = jest.fn();

  beforeEach(() => {
    wrapper = shallow(<Event delete={onDelete} event={event} />, { context });
  });

  it('should render component', () => {
    expect(wrapper.find('li').length).toBe(1);
  });

  it('should have edit link', () => {
    expect(wrapper.find({ to: `/user/event/edit/${event.id}` }).length).toBe(1);
  });

  it('should have delete link', () => {
    expect(wrapper.find('a').length).toBe(1);
  });

  it('delete link should point to the event id', () => {
    expect(wrapper.find('a').first().props().name).toBe(event.id);
  });

  it('should have view link', () => {
    expect(wrapper.find({ to: `/user/event/view/${event.id}` }).length).toBe(1);
  });

  it('should render event name', () => {
    expect(wrapper.find('.event-item p').first().text()).toBe(event.name);
  });

  describe('user clicks delete', () => {
    beforeEach(() => {
      const link = wrapper.find('a').first();
      link.simulate('click', {
        target: {
          name: event.id,
        },
      });
    });

    it('should send id as value onClick', () => {
      const invocationArgs = onDelete.mock.calls[0];
      expect(invocationArgs[0].target.name).toBe(event.id);
    });
  });
});
