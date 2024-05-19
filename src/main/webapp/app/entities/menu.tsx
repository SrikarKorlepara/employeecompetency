import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/department">
        <Translate contentKey="global.menu.entities.department" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/employee">
        <Translate contentKey="global.menu.entities.employee" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/competency">
        <Translate contentKey="global.menu.entities.competency" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/training-program">
        <Translate contentKey="global.menu.entities.trainingProgram" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/employee-training">
        <Translate contentKey="global.menu.entities.employeeTraining" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/performance-review">
        <Translate contentKey="global.menu.entities.performanceReview" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/skill-set">
        <Translate contentKey="global.menu.entities.skillSet" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
